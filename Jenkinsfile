pipeline {
    agent any

    stages {
        stage('Construir (Build)') {
            steps {
                echo '--- Compilando proyecto ---'
                dir('core-service') {
                    // CAMBIO: Agregamos "set JAVA_OPTS..." antes del gradlew
                    // Esto obliga a Java a usar el protocolo de seguridad correcto
                    bat 'set JAVA_OPTS=-Dhttps.protocols=TLSv1.2 && gradlew.bat clean build -x test'
                }
            }
        }

        stage('Desplegar a EC2') {
            steps {
                echo '--- Subiendo a la nube ---'
                sshagent(['llave-ec2-tinka']) {
                    // Esto se queda igual que antes
                    bat 'scp -o StrictHostKeyChecking=no core-service/build/libs/*.jar ec2-user@18.218.57.202:/home/ec2-user/app-tinka.jar'
                    bat 'ssh -o StrictHostKeyChecking=no ec2-user@18.218.57.202 "sudo systemctl restart tinka-backend.service"'
                }
            }
        }
    }
}