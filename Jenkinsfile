pipeline {
    agent any

    stages {
        stage('Construir (Build)') {
            steps {
                echo '--- Compilando forzando Java 8 ---'
                dir('core-service') {
                    // Esto ya sabemos que funciona perfecto
                    bat 'set "JAVA_HOME=D:\\jdk\\jdk1.8.0_181" && set "PATH=%JAVA_HOME%\\bin;%PATH%" && gradlew.bat clean build -x test'
                }
            }
        }

        stage('Desplegar a EC2') {
            steps {
                echo '--- Subiendo a la nube (Método Directo) ---'
                // CAMBIO MAESTRO: Usamos withCredentials en vez de sshagent
                // Esto crea un archivo temporal con tu llave solo por unos segundos
                withCredentials([sshUserPrivateKey(credentialsId: 'llave-ec2-tinka', keyFileVariable: 'MY_KEY')]) {
                    
                    // 1. Subir el archivo usando la llave (-i)
                    // Ojo a las comillas en "%MY_KEY%" para que Windows no moleste
                    bat 'scp -o StrictHostKeyChecking=no -i "%MY_KEY%" core-service/build/libs/core-service-0.0.1-SNAPSHOT.jar ec2-user@18.218.57.202:/home/ec2-user/app-tinka.jar'
                    
                    // 2. Reiniciar el servicio
                    bat 'ssh -o StrictHostKeyChecking=no -i "%MY_KEY%" ec2-user@18.218.57.202 "sudo systemctl restart tinka-backend.service"'
                }
            }
        }
    }
}