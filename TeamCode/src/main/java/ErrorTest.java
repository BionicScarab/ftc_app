import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Error")
public class ErrorTest extends OpMode {
    private DcMotor Motor_left = null;
    private DcMotor Motor_right = null;

    @Override
    public void init()  {
        //Motor_left  = hardwareMap.get(DcMotor.class, "left_drive");
       // Motor_right = hardwareMap.get(DcMotor.class, "right_drive");

        Motor_left.setDirection(DcMotor.Direction.FORWARD);
        Motor_right.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop()  {

        double rightPower = 0;
        double leftPower = 0;

        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x;

        Motor_left.setPower(leftPower);
        Motor_right.setPower(rightPower);
    }

    @Override
    public void stop()  {
    }
}
