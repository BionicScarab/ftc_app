
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RESET_ENCODERS;

@TeleOp(name="NormalOpMode")
public class TeleOpTest_single_stick extends OpMode
{
    private DcMotor Motor_left = null;
    private DcMotor Motor_right = null;

    private DcMotor arm_base = null;
    private DcMotor arm_shovel = null;

    private int ANDYMARK_FULL_REV = 1120;

    private DcMotor collector = null;

    public void setPowerChassis(double leftPower, double rightPower)    {
        Motor_left.setPower(leftPower);
        Motor_right.setPower(rightPower);
    }

    public void updateShovel()  {
        boolean isCollecting = false;
        boolean isDeployed = false;

        if (gamepad1.a)
            isCollecting = !isCollecting;
        if (isCollecting == true)
            collector.setPower(1);
        else
            collector.setPower(0);

        if(gamepad1.b)
            isDeployed = !isDeployed;

        arm_shovel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        if (isDeployed) {
            arm_shovel.setTargetPosition(-ANDYMARK_FULL_REV / 2);
        }
        else    {
            arm_shovel.setTargetPosition(ANDYMARK_FULL_REV / 2);
        }
        arm_shovel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double move_arm = -gamepad1.right_stick_y;

        arm_base.setPower(move_arm);
    }

    public void move()   {
        boolean front = true;

        if (gamepad1.x)
            front = !front;

        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.left_stick_x;

        double leftPower = Range.clip(drive + turn, -1.00, 1.00);
        double rightPower = Range.clip(drive - turn, -1.00, 1.00);

        if (front == true)
            setPowerChassis(leftPower, rightPower);
        else
            setPowerChassis(rightPower, leftPower);
    }

    @Override
    public void init()  {
        telemetry.addData("Status", "Initialized\n");
        telemetry.addData("Controls", "Left stick to move chassis\nRight stick to control base arm\nB to deploy\nA to collect\nX to switch front");

        Motor_left  = hardwareMap.get(DcMotor.class, "left_drive");
        Motor_right = hardwareMap.get(DcMotor.class, "right_drive");

        arm_base = hardwareMap.get(DcMotor.class, "arm1");
        arm_shovel = hardwareMap.get(DcMotor.class, "arm2");

        collector = hardwareMap.get(DcMotor.class, "collector");

        Motor_left.setDirection(DcMotor.Direction.FORWARD);
        Motor_right.setDirection(DcMotor.Direction.REVERSE);

        arm_base.setDirection(DcMotor.Direction.FORWARD);
        arm_shovel.setDirection(DcMotor.Direction.FORWARD);
        arm_shovel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        collector.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void loop()  {
        move();

        updateShovel();
    }

    @Override
    public void stop()  {
    }
}
