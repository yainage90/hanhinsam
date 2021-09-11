## 1. 소개

엘라스틱서치(ElasticSearch)에서 한글 검색 확장 기능을 위해 만든 토큰 필터 플러그인입니다.

지원하는 토큰 필터 종류는 아래와 같습니다.

**1. 자모 분리 필터(hanhinsam_jamo)**

한글 토큰 문자열을 자음과 모음 단위로 분리해줍니다. 한글은 유니코드 특성상 초성/중성/종성이 결합된 형태라 엘라스틱서치 자체의 Term Suggest API를 그대로 사용하기에는 무리가 있습니다. 따라서 자모단위로 분리된 필드를 추가적으로 색인하고 해당 필드를 통해 오타교정을 진행합니다.

**2. 초성 필터(hanhinsam_chosung)**

한글 토큰 문자열의 초성을 추출합니다. 초성 검색에 사용됩니다.

**3. 한 → 영 변환 필터(hanhinsam_hantoeng)**

한글 토큰 문자열을 자모 단위로 분해한 후 키보드 배열에 매칭되는 영어 문자열로 변환합니다. 해당 필


<br>

## 2. 빌드 및 설치

#### 2-1. 빌드

프로젝트를 clone하고 프로젝트 루트 디렉터리에서 gradle 빌드 실행

``` shell
./gradlew clean assemble
```

<br>

#### 2-2. 플러그인 zip 압축파일 생성 확인

build/distributions/hanhinsam-0.0.1-SNAPSHOT.zip

![hanhinsam_zip](./images/hanhinsam_zip.png)

1. jar 라이브러리
2. plugin-descriptor.properties

<br>

#### 2-3. 엘라스틱서치에 플러그인 설치

elasticsearch/bin/plugin-install을 실행하여 플러그인을 설치합니다.

``` shell
sudo bin/elasticsearch-plugin install file://<path_to_hanhinsam_zip>
```

![hanhinsam_install](./images/hanhinsam_install.png)

<br>

docker-elk를 사용하는 경우 elasticsearch Dockerfile로 이미지 빌드시에 플러그인을 설치할 수 있습니다.

``` yml
...

COPY plugins/hanhinsam-0.0.1-SNAPSHOT.zip /plugins/hanhinsam.zip

RUN elasticsearch-plugin install analysis-nori
RUN elasticsearch-plugin install file:///plugins/hanhinsam.zip 
```

#### 2-4. 엘라스틱서치 재시작

엘라스틱서치를 실행중이었다면 재시작해야 플러그인이 적용됩니다. 

<br>

## 3. 예제

**1) 오타 교정**

자모 분리한 문자열을 색인하기 위한 필드를 추가적으로 생성합니다. 해당 필드는 분석이 필요하므로 `text` 타입입니다. 이 필드를 분석하기 위한 분석기를 만들고 필터에 `jamo_filter`를 적용합니다. Term Suggest API 를 사용하면 해당 필드를 통해 오타 교정이 가능합니다.

``` javascript
PUT /spell_test
{
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 0,
    "index.max_ngram_diff": 10,
    "analysis": {
      "filter": {
        "jamo_filter": {
          "type": "hanhinsam_jamo"
        }
      },
      "analyzer": {
        "jamo_analyzer": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "jamo_filter"
          ]
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "name": {
        "type": "keyword",
        "copy_to": ["name_jamo"]
      },
      "name_jamo": {
        "type": "text",
        "analyzer": "jamo_analyzer"
      }
    }
  }
}

POST /_bulk
{ "index" : { "_index" : "spell_test", "_id" : "1" } }
{ "name" : "손오공" }
{ "index" : { "_index" : "spell_test", "_id" : "2" } }
{ "name" : "엘라스틱서치" }
{ "index" : { "_index" : "spell_test", "_id" : "3" } }
{ "name" : "아메리카노" }

POST /spell_test/_search
{
  "suggest": {
    "name_suggest": {
      "text": "아메리치노",
      "term": {
        "field": "name_jamo",
        "max_edits": 2
      }
    }
  }
}
```

**2) 한/영 변환 오타 교정**

한/영 변환한 문자열을 색인하기 위한 필드를 각각 추가적으로 생성합니다. 해당 필드는 분석이 필요하기때문에 `text` 타입입니다. 한/영 변환 필터가 적용된 분석기를 만들고 각 분석기를 한/영 변환 필드의 `search_analyzer`로 지정합니다.

``` javascript
PUT /haneng_test
{
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 0,
    "index.max_ngram_diff": 10,
    "analysis": {
      "filter": {
        "engtohan_filter": {
          "type": "hanhinsam_engtohan"
        },
        "hantoeng_filter": {
          "type": "hanhinsam_hantoeng"
        }
      },
      "analyzer": {
        "engtohan_analyzer": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "engtohan_filter"
          ]
        },
        "hantoeng_analyzer": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "hantoeng_filter"
          ]
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "name": {
        "type": "keyword",
        "copy_to": ["name_hantoeng", "name_engtohan"]
      },
      "name_hantoeng": {
        "type": "text",
        "search_analyzer": "hantoeng_analyzer"
      },
      "name_engtohan": {
        "type": "text",
        "search_analyzer": "engtohan_analyzer"
      }
    }
  }
}

POST /_bulk
{ "index" : { "_index" : "haneng_test", "_id" : "1" } }
{ "name" : "손오공" }
{ "index" : { "_index" : "haneng_test", "_id" : "2" } }
{ "name" : "elastic" }
{ "index" : { "_index" : "haneng_test", "_id" : "3" } }
{ "name" : "아메리카노" }


POST /haneng_test/_search
{
  "query": {
    "match": {
      "name_hantoeng": "딤ㄴ샻"
    }
  }
}

POST /haneng_test/_search
{
  "query": {
    "match": {
      "name_engtohan": "thsdhrhd"
    }
  }
}
```

**3) 초성 검색**

초성이 분리된 문자열을 색인하기 위한 `text` 타입의 필드를 추가적으로 생성하고 초성 필터가 적용된 분석기를 만듭니다. 이후 해당 필드를 통해 초성 검색이 가능합니다.

``` javascript
PUT /chosung_test
{
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 0,
    "index.max_ngram_diff": 10,
    "analysis": {
      "filter": {
        "chosung_filter": {
          "type": "hanhinsam_chosung"
        }
      },
      "analyzer": {
        "chosung_analyzer": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "chosung_filter"
          ]
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "name": {
        "type": "keyword",
        "copy_to": ["name_chosung"]
      },
      "name_chosung": {
        "type": "text",
        "analyzer": "chosung_analyzer"
      }
    }
  }
}

POST /_bulk
{ "index" : { "_index" : "chosung_test", "_id" : "2" } }
{ "name" : "엘라스틱서치" }
{ "index" : { "_index" : "chosung_test", "_id" : "3" } }
{ "name" : "아메리카노" }

POST /chosung_test/_search
{
  "query": {
    "match": {
      "name_chosung": "ㅇㄹㅅㅌㅅㅊ"
    }
  }
}
```

**4) 자동완성**

자동완성을 위한 `text`타입의 필드를 추가적으로 생성합니다. 색인 분석기는 ngram 토크나이저를 통해 부분 문자열이 같이 색인되도록 합니다. 검색 분석기에는 `jamo_filter`만 적용합니다. 이후 분석을 위해 추가 생성한 필드를 통해 부분일치를 통한 검색이 가능하며 이 기능을 통해 서비스에서는 자동완성 기능을 구현할 수 있게됩니다.

``` javascript
PUT /ac_test
{
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 0,
    "index.max_ngram_diff": 30,
    "analysis": {
      "filter": {
        "ngram_filter": {
          "type": "ngram",
          "min_gram": 1,
          "max_gram": 20
        },
        "jamo_filter": {
          "type": "hanhinsam_jamo"
        }
      },
      "analyzer": {
        "jamo_analyzer": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "jamo_filter"
          ]
        },
        "ngram_jamo_analyzer": {
          "type": "custom",
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "jamo_filter",
            "ngram_filter"
          ]
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "name": {
        "type": "keyword",
        "copy_to": "name_ngram"
      },
      "name_ngram": {
        "type": "text",
        "analyzer": "ngram_jamo_analyzer",
        "search_analyzer": "jamo_analyzer"
      }
    }
  }
}

POST /_bulk
{ "index" : { "_index" : "ac_test", "_id" : "1" } }
{ "name" : "손오공" }
{ "index" : { "_index" : "ac_test", "_id" : "2" } }
{ "name" : "elastic" }
{ "index" : { "_index" : "ac_test", "_id" : "3" } }
{ "name" : "아메리카노" }

POST /ac_test/_search
{
  "query": {
    "match": {
      "name_ngram": "아멜"
    }
  }
}
```