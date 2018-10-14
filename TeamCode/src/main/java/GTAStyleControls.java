import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "GTA controls")
public class GTAStyleControls extends OpMode {

    private DcMotor Motor_left = null;
    private DcMotor Motor_right = null;

    @Override
    public void init()  {
        Motor_left  = hardwareMap.get(DcMotor.class, "left_drive");
        Motor_right = hardwareMap.get(DcMotor.class, "right_drive");

        Motor_left.setDirection(DcMotor.Direction.FORWARD);
        Motor_right.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop()  {

        double rightPower;
        double leftPower;

        double drive =  gamepad1.right_trigger - gamepad1.left_trigger;
        double turn  =  gamepad1.left_stick_x;

        leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
        rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

        Motor_left.setPower(leftPower);
        Motor_right.setPower(rightPower);
    }

    @Override
    public void stop()  {
    }
}
