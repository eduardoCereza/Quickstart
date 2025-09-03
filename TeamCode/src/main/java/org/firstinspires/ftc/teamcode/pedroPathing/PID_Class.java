package org.firstinspires.ftc.teamcode.pedroPathing;

import com.qualcomm.robotcore.hardware.DcMotor;

public class PID_Class {
    double kp, ki, kd, error, integral, derivada, current, target, output, lastError = 0;

    public PID_Class(double KP, double KI, double KD, int input, int target){
        this.kp = KP;
        this.ki = KI;
        this.kd = KD;
        this.current = input;
        this.target = target;
    }

    public void pid_system(DcMotor motor){
        error = current - target;

        //Proporcional
        double Proporcional = error * kp;

        //Integral
        integral =- error;
        double Integral = integral * ki;

        //Derivada
        derivada = error - lastError;
        double Derivada = derivada * kd;

        //Correção
        output = Proporcional + Integral + Derivada;

        //Saída para o motor
        if (current < target) {
            motor.setPower(output);
        }else{
            motor.setPower(-output);
        }
        motor.setPower(0);
        lastError = error;
    }

}