import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;
import com.sun.tools.javac.tree.DCTree;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RESET_ENCODERS;

@TeleOp(name="NormalOpMode")
public class TeleOpTest_single_stick extends OpMode
{
    private DcMotor leftDrive  = null;
    private DcMotor rightDrive = null;

    private DcMotor armBase = null;
    private DcMotor shovel = null;
    private DcMotor collector = null;

    private int TETRIX_MOTOR_REV = 1440;
    private int ANDYMARK_MOTOR_REV = 1120;

    private long startTimeCollector   = 0;
    private long currentTimeCollector = 0;
    private long deltaTimeCollector   = 0;
    private boolean isCollecting = false;

    private boolean isDeployed = false;
    private long startTimeDeployer = 0;
    private long currentTimeDeployer = 0;
    private long deltaTimeDeployer = 0;

    public long pow(int x, int y)   {
        if (y == 0) {
            return (1);
        }
        else if (y == 1){
            return (x);
        }
        else    {
            return (x * pow(x, y - 1));
        }
    }

    public void move()  {
        double  drive = -gamepad1.left_stick_y;
        double  turn  = -gamepad1.left_stick_x / 2;

        double  leftPower  = Range.clip(drive + turn, -1, 1);
        double  rightPower = Range.clip(drive - turn, -1, 1);

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }

    public void updateArm() {
        double  moveArm      = -gamepad1.right_stick_y;
        long    divideToSec  = pow(10, 9);
        //double  shovelPower  = (gamepad1.left_trigger - gamepad1.right_trigger)/2;

        armBase.setPower(moveArm);
        //shovel.setPower(shovelPower);

        currentTimeCollector = System.nanoTime();
        deltaTimeCollector= (currentTimeCollector - startTimeCollector) / (divideToSec / 10);
        if (gamepad1.a && deltaTimeCollector > 5) {
            startTimeCollector = System.nanoTime();
            if (isCollecting)   {
                collector.setPower(1);
                isCollecting = false;
            }
            else if (!isCollecting) {
                collector.setPower(0);
                isCollecting = true;
            }
        }

        currentTimeDeployer = System.nanoTime();
        deltaTimeDeployer = (currentTimeDeployer - startTimeDeployer) / (divideToSec / 10);
        if (gamepad1.x && deltaTimeDeployer > 5)    {
            startTimeDeployer = System.nanoTime();
            if (isDeployed) {
                isDeployed = false;
                shovel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                shovel.setTargetPosition(-TETRIX_MOTOR_REV / 6);
                shovel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                while (shovel.isBusy())
                    shovel.setPower(1);
                shovel.setPower(0);
            }
            else    {
                isDeployed = true;
                shovel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                shovel.setTargetPosition(TETRIX_MOTOR_REV / 6);
                shovel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                while (shovel.isBusy())
                    shovel.setPower(1);
                shovel.setPower(0);
            }
        }
    }

    @Override
    public void init()  {
        telemetry.addData("Status", "Initialized\n");
        startTimeCollector   = System.nanoTime();
        currentTimeCollector = System.nanoTime();
        startTimeDeployer = System.nanoTime();
        currentTimeDeployer = System.nanoTime();

        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        armBase    = hardwareMap.get(DcMotor.class, "arm_base");
        shovel     = hardwareMap.get(DcMotor.class, "shovel");
        collector  = hardwareMap.get(DcMotor.class, "collector");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        armBase.setDirection(DcMotor.Direction.FORWARD);
        shovel.setDirection(DcMotor.Direction.FORWARD);
        collector.setDirection(DcMotor.Direction.REVERSE);

        armBase.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shovel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop()  {
        move();
        updateArm();
        telemetry.addLine("\n");
        telemetry.addData("deltaTime: ", deltaTimeCollector / 10.0);
    }

    @Override
    public void stop()  {
        telemetry.clear();
        telemetry.addData("Status: ", "Stopped");
    }
}