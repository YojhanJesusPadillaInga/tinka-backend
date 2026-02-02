pipeline {
    agent any

    stages {
        stage('Construir (Build)') {
            steps {
                echo '--- Compilando con Gradle ---'
                // Damos permiso de ejecución al "wrapper" de Gradle
                sh 'chmod +x gradlew'
                // Compilamos creando el jar (saltando tests para ir rápido)
                sh './gradlew clean build -x test'
            }
        }

        stage('Desplegar a EC2') {
            steps {
                echo '--- Subiendo a la nube ---'
                sshagent(['llave-ec2-tinka']) {
                    // 1. Subir el jar a tu IP 18.218.57.202
                    // OJO: Si falla diciendo que no encuentra el archivo, revisa si Gradle lo crea en build/libs/
                    sh 'scp -o StrictHostKeyChecking=no build/libs/*.jar ec2-user@18.218.57.202:/home/ec2-user/app-tinka.jar'
                    
                    // 2. Reiniciar el servicio en tu servidor
                    // IMPORTANTE: Esto solo funciona si ya creaste el servicio "tinka-backend.service"
                    sh 'ssh -o StrictHostKeyChecking=no ec2-user@18.218.57.202 "sudo systemctl restart tinka-backend.service"'
                }
            }
        }
    }
}