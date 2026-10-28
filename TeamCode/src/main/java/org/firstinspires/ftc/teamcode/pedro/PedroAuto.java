package org.firstinspires.ftc.teamcode.pedro;
import com.pedropathing.ivy.Command;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

@Autonomous
public class PedroAuto extends OpMode {
    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees(); // the factory we added before, we are still at the top of the class
    // our poses
    private final Pose startPose = p.of(0, 0, 0);
    private final Pose park = p.of(24, 24, 90); // add this
    private Path park() {
        return line(startPose, park).linear(startPose, park);
    }
    private Path drive() {
        return line(park, park).linear(startPose, park);
    }
    private Command commandTest = parallel();

    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
    }

    @Override
    public void start() {
        schedule(follow(follower, park()));

        // using the same park path from the last page
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
//        String drivetrainString = follower.debug().drivetrain().toString();
//        telemetry.addLine(drivetrainString);
        telemetry.addLine("Distance to endpoint:" + String.valueOf(follower.distanceToEndpoint()));
        telemetry.update();
    }
}