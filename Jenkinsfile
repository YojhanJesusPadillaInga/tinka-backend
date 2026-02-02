pipeline {
    agent any

    stages {
        stage('Construir (Build)') {
            steps {
                echo '--- Compilando proyecto ---'
                // CAMBIO 1: Le decimos a Jenkins "Entra a la carpeta core-service"
                dir('core-service') {
                    // Ahora sí va a encontrar el gradlew.bat
                    bat 'gradlew.bat clean build -x test'
                }
            }
        }

        stage('Desplegar a EC2') {
            steps {
                echo '--- Subiendo a la nube ---'
                sshagent(['llave-ec2-tinka']) {
                    // CAMBIO 2: La ruta del JAR ahora incluye 'core-service/' al principio
                    bat 'scp -o StrictHostKeyChecking=no core-service/build/libs/*.jar ec2-user@18.218.57.202:/home/ec2-user/app-tinka.jar'
                    
                    // Esto sigue igual
                    bat 'ssh -o StrictHostKeyChecking=no ec2-user@18.218.57.202 "sudo systemctl restart tinka-backend.service"'
                }
            }
        }
    }
}