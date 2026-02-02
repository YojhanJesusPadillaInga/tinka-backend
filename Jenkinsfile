pipeline {
    agent any

    stages {
        stage('Construir (Build)') {
            steps {
                echo '--- Compilando para Windows ---'
                // Windows no necesita chmod.
                // Usamos 'bat' y llamamos al archivo .bat de Gradle
                bat 'gradlew.bat clean build -x test'
            }
        }

        stage('Desplegar a EC2') {
            steps {
                echo '--- Subiendo a la nube ---'
                sshagent(['llave-ec2-tinka']) {
                    // Usamos 'bat' en vez de 'sh' para los comandos SSH/SCP
                    
                    // 1. Subir el JAR
                    bat 'scp -o StrictHostKeyChecking=no build/libs/*.jar ec2-user@18.218.57.202:/home/ec2-user/app-tinka.jar'
                    
                    // 2. Reiniciar el servicio
                    bat 'ssh -o StrictHostKeyChecking=no ec2-user@18.218.57.202 "sudo systemctl restart tinka-backend.service"'
                }
            }
        }
    }
}