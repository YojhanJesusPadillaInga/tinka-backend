pipeline {
    agent any

    stages {
        stage('Construir (Build)') {
            steps {
                echo '--- Compilando forzando Java 8 ---'
                dir('core-service') {
                    bat 'set "JAVA_HOME=D:\\jdk\\jdk1.8.0_181" && set "PATH=%JAVA_HOME%\\bin;%PATH%" && gradlew.bat clean build -x test'
                }
            }
        }

        stage('Desplegar a EC2') {
            steps {
                echo '--- Subiendo a la nube (Con corrección de permisos) ---'
                
                withCredentials([sshUserPrivateKey(credentialsId: 'llave-ec2-tinka', keyFileVariable: 'MY_KEY')]) {
                    
                    // PASO 1 (NUEVO): Corregir permisos de la llave en Windows
                    // "icacls" elimina la herencia (/inheritance:r) y solo da permiso al dueño actual (/grant:r)
                    bat 'icacls "%MY_KEY%" /inheritance:r /grant:r SYSTEM:F'
                    
                    // PASO 2: Subir el archivo (Ahora SSH aceptará la llave)
                    bat 'scp -o StrictHostKeyChecking=no -i "%MY_KEY%" core-service/build/libs/core-service-0.0.1-SNAPSHOT.jar ec2-user@18.218.57.202:/home/ec2-user/app-tinka.jar'
                    
                    // PASO 3: Reiniciar el servicio
                    bat 'ssh -o StrictHostKeyChecking=no -i "%MY_KEY%" ec2-user@18.218.57.202 "sudo systemctl restart tinka-backend.service"'
                }
            }
        }
    }
}