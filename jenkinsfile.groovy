import java.text.SimpleDateFormat
def TODAY = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())
pipeline {
    agent any   // 개별 스테이지마다 agent 지정
    environment {
        strDockerTag = "${TODAY}_${BUILD_ID}"
        strDockerImage ="hothernki/cicd_coreflow:${strDockerTag}"
        strGitUrl = "https://github.com/kh-NG999/COREFLOW_BACKEND.git"
    }
    stages {
        // 1. 깃헙 체크아웃(master)
        stage('Checkout') {
            steps {
                git branch: 'main', url: strGitUrl
            }
        }
        // 2. 소스코드 빌드
        stage('Build') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean package'
            }
        }
        // 3. 도커 이미지 빌드
        stage('Docker Image Build') {
            steps {
                script {
                    oDockImage = docker.build(strDockerImage, "--build-arg VERSION="+strDockerTag+" -f Dockerfile .")
                }
            }
        }
        // 4. 도커 이미지 푸쉬
        stage('Docker Image Push') {
            steps {
                script {
                    docker.withRegistry('', 'Dockerhub_Cred') {
                        oDockImage.push()
                    }
                }
            }
        }
        // 5. 프로덕션 서버 배포
        stage('Deploy Production') {
            steps {
                sshagent(credentials: ['SSH-PrivateKey']) {
                    sh "ssh -o StrictHostKeyChecking=no ec2-user@ docker container rm -f coreflow-back"
                    sh "ssh -o StrictHostKeyChecking=no ec2-user@ docker container run \
                                        -d \
                                        -p 8081:10000  \
                                        --name=guestbookapp \
                                        -e MYSQL_IP=172.31.0.120 \
                                        -e MYSQL_PORT=3306 \
                                        -e MYSQL_DATABASE=guestbook \
                                        -e MYSQL_USER=guestbook \
                                        -e MYSQL_PASSWORD=education \
                                        wombat1234/cicd_guestbook:1.0 "
                }
            }
        }
    }
}