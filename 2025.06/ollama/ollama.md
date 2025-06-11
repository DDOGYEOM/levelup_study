# Ollama 소개

안녕하세요 김도겸입니다.  
이번 달 2주차 스터디는 Ollama에 대해 간략히 소개해보고 직접 사용해보며 느낀 점에 대해 발표해보도록 하겠습니다.

<br>

## LLM 짚고 넘어가기

본격적인 소개에 앞서 가장 기반이 되는 개념인 LLM에 대해 알아보도록 하겠습니다.

요즘 ChatGPT와 같은 대화형 인공지능 서비스는 굉장히 주목할 만한 핵심 분야이자, 실생활에서 유용하게 사용되고 있는데요.  
특히 저희와 같은 개발자들에겐 없어서는 안 될 서비스입니다.

![gpt_dev_meme_1](./images/gpt_dev_meme_1.jpg) ![gpt_dev_meme_2](./images/gpt_dev_meme_2.jpg)

> 수많은 개발자들의 스트레스성 질환을 해결해주는 명의가 되어가고 있지만, 가끔 발목을 심하게 잡을 때도 있습니다 😅  
> 제 미래 진로에 있어... 위협을 가할 때도 있구요.. 😂

![llm_brain](./images/llm_brain.jpg)

LLM(Large Language Model)이란 방대한 양의 대규모 텍스트 데이터셋로 사전 학습된 초대형 딥러닝 모델입니다.  
단순한 텍스트 생성, 번역, 요약, 질문 응답에서 나아가 최근엔 자율적으로 실시간 검색을 통해 이공계열 문제 및 상식 등을 추론해내는 기능도 해내고 있습니다.

![llm_works](./images/llm_works.jpg)

LLM은 입력 텍스트를 토큰화하여 벡터로 변환하고, Transformer 레이어를 통해 문맥적 의미를 파악하여, 다음에 올 토큰을 예측하는 방식으로 텍스트를 생성해 과정을 거쳐 데이터를 제공합니다.

![llm_example](./images/llm_example.avif)

대표적인 LLM으론 Claude, Gemini, GPT 등이 있습니다.

<br><br>

## Local LLM

### 🔍 Local LLM 기본 설명

흔히 우리가 사용하는 인공지능 서비스들은 웹 상에서 사이트를 접속하거나, 앱을 실행하여 제공되는 서비스를 활용합니다.  
즉 Online 상의 각 기업에서 제공하는 클라우드 서버에 LLM이 내장되어 있고, 이를 활용하기 위해선 인터넷에 접속하여 해당 서비스 사이트에 접속하여 사용해야 하는 것이죠.  
그런데 저희가 주로 업무하는 금융권 회사들은 폐쇄적인 환경 속에서 개발 업무를 진행해야 합니다.
내부에서 chat gpt를 사용하려고 한다면 외부망이 연결된 PC를 활용해야 하거나, 이 또한 없으면 핸드폰으로 접속하여야 하는데 실제로 업무해보신 분들은 아시겠지만.. 굉장히 번거롭고 힘든 과정입니다. 특히 검색할 내용이 많다면 복/붙이 안된다는 단점도 있으며 민감한 개인정보가 오가는 경우가 많기 때문에 더더욱 쓰기가 두려워지죠.

이러한 문제를 해결하기 위해 나온 것이 바로 Local LLM입니다.

![local_llm_vs_general_llm](./images/local_llm_vs_general_llm.jpg)

Local LLM은 용어 그대로 개인 PC 또는 서버에 설치하여 local 환경에서 실행 가능한 LLM을 의미합니다.
ChatGpt와 같은 기존 서비스에선 인터넷을 통해 서비스 제공자의 서버로 전송되며 거기에서 처리된 후 결과를 반환 시켜주는 것과 달리 파일로서 공개된 모델을 자신의 PC에 설치하여 개인 PC 내부에서 이 과정을 처리합니다.

최근엔 이 Local LLM을 위해 Llama, Mistral, Phi, Gemma 등의 오픈소스 모델들이 공개되어 개인 PC에서도 잘 작동하도록 크기 및 성능을 조절하여 최적화한 모델도 늘고 있습니다.

### ✅ 장단점

&nbsp; **-장점-**

- **보안:** 데이터 유출 위험 감소
- **개인 정보 보호:** 민감한 정보 안전하게 처리
- **오프라인 사용:** 인터넷 연결 없이 사용 가능
- **커스터마이징:** 특정 목적에 맞게 모델 fine-tuning 가능
  > <b>fine-tuning:</b> 학습된 모델을 특정 작업이나 응용 프로그램에 맞게 조정하는 과정을 의미합니다.

&nbsp; **-단점-**

- **높은 초기 비용:** 고성능 GPU 필요
- **기술적 지식 필요:** 설치 및 설정에 대한 이해 필요
- **모델 관리:** 모델 업데이트 및 유지보수 필요

<br><br>

## Ollama

Local LLM에 대해 알아 보았는데요.  
모두 아시다시피 이 Local LLM만 있어서는 우리와 같은 일반 사용자들은 관리하거나, 사용하고 실행할 수 없습니다.  
그렇기 때문에 Local LLM을 실행하기 위한 다양한 툴을 각종 기업에서 제공하고 있고, 그 중 가장 많이 사용하는 툴이 바로 오늘 발표 주제인 <b>Ollama</b>입니다.

### 🔍 What is Ollama?

![ollama](./images/ollama.png)

<b>Ollama</b>는 Local LLM을 로컬 환경에서 실행할 수 있게 해주는 오픈 소스 도구입니다.  
LLM 사용의 진입 장벽을 낮추고 개발자들이 쉽게 LLM을 활용할 수 있도록 도우며, Local LLM 생태계를 활성화시키는 것이 Ollama의 목표로, 현재 다양한 개발 분야에 쉽게 사용될 수 있도록 200명 이상의 기여자가 GitHub에서 활발히 활동하고 있고 타 도구에 비해 가장 활발한 커뮤니티를 가졌습니다.

<br>

### 🛠️ 핵심 특징 & 아키텍처

<br>

&nbsp; **컨테이너 기반 아키텍처**

- LLM을 컨테이너 이미지로 관리
- 모델, 의존성, 설정 등을 하나의 패키지로 묶어 배포
- 격리된 환경에서 실행하여 안정성 확보

&nbsp; **레이어링 시스템**

- 기본 모델 위에 추가적인 레이어를 쌓아 커스터마이징
- 프롬프트 템플릿, 시스템 메시지 등을 레이어로 관리
- 재사용성 및 유지보수성 향상

&nbsp; **API 서버**

- Local LLM을 API 형태로도 제공.
- HTTP 엔드포인트를 통해 LLM 기능 사용 \* 다양한 프로그래밍 언어에서 LLM 활용 가능

<br>

### ✅ 장단점

&nbsp; **-장점-**

- **간편한 설치 및 사용:** 몇 번의 명령어로 LLM 설치 및 실행 가능
- **다양한 모델 지원:** Llama 2, Mistral, Gemma 등 다양한 모델 지원
- **크로스 플랫폼:** macOS, Linux, Windows 지원
- **커뮤니티 지원:** 활발한 커뮤니티를 통해 정보 공유 및 문제 해결 가능

&nbsp; **-단점-**

- **GPU 성능 의존적:** LLM 추론 속도는 GPU 성능에 따라 달라짐
- **모델 크기 제한:** GPU 메모리 크기에 따라 사용 가능한 모델 크기 제한
- **초기 모델 다운로드 시간:** 모델 크기가 클 경우 다운로드 시간 소요

<br>

### ⚙️ 기본 사용법

Ollama의 특징과 장단점을 알아보았으니 이제 Ollama의 기본 기능을 사용해보도록 하겠습니다.  
(예시 이미지는 윈도우를 기준으로 하였습니다.)

- <b>Ollama 설치</b>
  ![ollama_download](./images/ollama_download.png)  
  [https://ollama.com/download](https://ollama.com/download)에 접속하여 본인이 원하는 OS를 클릭한 후 다운로드받습니다.

  ![ollama_setup_file](./images/ollama_setup_file.png)  
  다운로드가 완료되었다면 이 OllamaSetup.exe 파일이 있을 것입니다. 클릭하여 실행시켜주세요.

  ![ollama_install](./images/ollama_install.png)  
  실행하시고 Install 버튼을 클릭하여 설치하면 됩니다.

  ![ollama_install_check](./images/ollama_install_check.png)  
  설치가 제대로 되었는 지 확인해보기 위해 cmd를 키고 `ollama` 명령어를 쳐봅시다.  
  관련 명령어에 대한 설명을 보여주는 것을 보니 정상적으로 설치된 것 같습니다.👍

  > 설치 확인은 `localhost:11434` 에 접속하여 확인도 가능합니다.

- <b>Ollama 자체 실행하기</b>  
  이제 설치가 완료되었으니 이제 cmd에서 Ollama를 활용해 Local LLM을 install하여 실행해보도록 하겠습니다.  
  먼저 사용할 Local LLM을 찾아봐야겠죠?

  ![ollama_llm_search](./images/ollama_llm_search.png)  
   [https://ollama.com/search](https://ollama.com/search)에 접속하여 찾아본 후 마음에 드는 LLM을 선택합니다.

  실행하는 방법은 간단합니다. `ollama run [활용할 LLM]` 명령어를 입력하면 됩니다.  
  만약 해당 모델이 이미 설치되어 있다면 실행이 될 것이고 설치가 되어 있지 않다면 아래 이미지와 같이 모델 설치를 진행합니다.

  ![ollama_llm_install](./images/ollama_llm_install.png)

  > 저는 LG에서 개발한 Local LLM인 Exaone 3.5를 사용해보았습니다.

  설치가 완료되면 자동으로 실행이 되며 이제 gpt를 사용하는 것처럼 질문해보면 됩니다. 🙂  
  ![ollama_llm_install_success](./images/ollama_llm_install_success.png)  
   ![ollama_llm_run](./images/ollama_llm_run.png)  
   ![exaone_run_1](images/exaone_run_1.png)

<br>

### 🖥️ 활용 방안

- docker를 활용한 서버 내 LLM 구축
- 다양한 기술 연동
- 커스텀 챗봇 구축

## 마무리

Ollama에 대해 알아보았습니다.  
단순히 `폐쇄된 환경에서 Chat GPT를 사용할 수 없을까` 라는 생각으로 찾아보기 시작한 기술이었지만 생각보다 다양한 방식으로 활용하여 `내 컴퓨터 안의 작은 전용 AI`를 갖출 수 있다는 것에 신기함을 느끼게 되는 시간이었습니다.  
물론 현재 chatGPT, Gemini와 같은 생성형 AI의 기능을 갖추기엔 부족하지만 분명 주목해봐야 할 기술이며, 보안이 중요시되는 분야와 밀접하게 연관된 저희의 업무에도 알아두면 분명 도움이 되지 않을까 생각해봅니다.  
추후에 기회가 된다면 관련 지식을 공부한 후 간단한 로컬 챗봇을 한 번 만들어 봐도 좋을 것 같습니다.

감사합니다😄
