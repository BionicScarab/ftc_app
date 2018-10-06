import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Test")

public class TeleOpTest extends OpMode {

    private static final float   ON = 1;
    //private static final float   OFF = 0;

    private DcMotor Motor_left = null;
    private DcMotor Motor_right = null;

    @Override
    public void init()  {
        Motor_left  = hardwareMap.get(DcMotor.class, "left_drive");
        Motor_right = hardwareMap.get(DcMotor.class, "right_drive");

        Motor_left.setDirection(DcMotor.Direction.FORWARD);
        Motor_right.setDirection(DcMotor.Direction.REVERSE);
    }

    public void move(String MovementType, String Movement)  {
        if (MovementType == "linear")   {
            if (Movement == "forward")
            {
                Motor_left.setPower(ON);
                Motor_right.setPower(ON);
            }
            else if (Movement == "backward") {
                Motor_left.setPower(-ON);
                Motor_right.setPower(-ON);
            }
        }
        else if (MovementType == "rotate") {
            if (Movement == "right") {
                Motor_right.setPower(ON);
                Motor_left.setPower(-ON);
            } else if (Movement == "left") {
                Motor_right.setPower(-ON);
                Motor_left.setPower(ON);
            }
        }
    }

    @Override
    public void loop()  {
        if (gamepad1.left_stick_y == -1)    {
            move("linear", "forward");
        }
        if (gamepad1.left_stick_y == 1 )    {
            move("linear", "backward");
        }
        if (gamepad1.right_stick_x == 1)    {
            move("rotate", "right");
        }
        if (gamepad1.right_stick_x == -1)   {
            move("rotate", "left");
        }
    }

    @Override
    public void stop()  {
    }
}
