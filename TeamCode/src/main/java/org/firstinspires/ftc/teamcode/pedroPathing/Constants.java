package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(8)
            .forwardZeroPowerAcceleration(42.2629)
            .lateralZeroPowerAcceleration(74.80048)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.1 , 0 , 0.01, 0.02))
            .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.2, 0, 0.01, 0.015))

            .headingPIDFCoefficients(new PIDFCoefficients(0.4, 0, 0.015, 0.02))
            .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(1, 0, 0.04, 0.01))

            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.0065, 0, 0.0001, 0.6, 0.02))
            .secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(0.0001, 0, 0.00001, 0.6, 0.01))

            .centripetalScaling(0.0001)            ;
    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("rightFront")
            .rightRearMotorName("rightRear")
            .leftRearMotorName("leftRear")
            .leftFrontMotorName("leftFront")
            .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .xVelocity(84.37307763287401)
            .xVelocity(70.68800077663633)
            ;
    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(5)
            .strafePodX(20)
            .distanceUnit(DistanceUnit.CM)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);
    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 0.45, 1);
    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .pinpointLocalizer(localizerConstants)
                .build();
    }
}
