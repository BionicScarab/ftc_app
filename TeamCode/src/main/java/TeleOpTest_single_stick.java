
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Test_single_stick")
public class TeleOpTest_single_stick extends OpMode
{
    private DcMotor Motor_left = null;
    private DcMotor Motor_right = null;

    public void setPowerChassis(double leftPower, double rightPower)    {
        Motor_left.setPower(leftPower);
        Motor_right.setPower(rightPower);
    }

    @Override
    public void init()  {
        Motor_left  = hardwareMap.get(DcMotor.class, "left_drive");
        Motor_right = hardwareMap.get(DcMotor.class, "right_drive");

        Motor_left.setDirection(DcMotor.Direction.FORWARD);
        Motor_right.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop()  {

        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.left_stick_x;

        double leftPower = Range.clip(drive + turn, -1.00, 1.00);
        double rightPower = Range.clip(drive - turn, -1.00, 1.00);
        setPowerChassis(leftPower, rightPower);

    }

    @Override
    public void stop()  {
    }
}
