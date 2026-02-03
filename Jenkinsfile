pipeline {
    agent any

    stages {
        stage('Construir (Build)') {
            steps {
                echo '--- Compilando forzando Java 8 ---'
                dir('core-service') {
                    // AQUÍ ESTÁ LA MAGIA:
                    // 1. Forzamos a JAVA_HOME a ser tu ruta de Java 8 (D:\jdk\jdk1.8.0_181)
                    // 2. Ponemos esa ruta PRIMERO en el PATH para que Windows no se confunda.
                    // 3. Ejecutamos Gradle (Recuerda haber bajado la versión a 6.9 en el archivo properties)
                    bat 'set "JAVA_HOME=D:\\jdk\\jdk1.8.0_181" && set "PATH=%JAVA_HOME%\\bin;%PATH%" && gradlew.bat clean build -x test'
                }
            }
        }

        stage('Desplegar a EC2') {
            steps {
                echo '--- Subiendo a la nube ---'
                sshagent(['llave-ec2-tinka']) {
                    // Subimos el archivo compilado
                    bat 'scp -o StrictHostKeyChecking=no core-service/build/libs/core-service-0.0.1-SNAPSHOT.jar ec2-user@18.218.57.202:/home/ec2-user/app-tinka.jar'
                    
                    // Reiniciamos el servicio
                    bat 'ssh -o StrictHostKeyChecking=no ec2-user@18.218.57.202 "sudo systemctl restart tinka-backend.service"'
                }
            }
        }
    }
}