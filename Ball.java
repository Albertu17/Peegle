package PreProjet;
public class Ball{

    public final double ballRadius = 25.0; // m

    public double ballX, ballY; // m
    public double ballSpeedX, ballSpeedY; // m

    private int height=500;
    private int width=500;

    Ball(){
        ballX=width/2;
        ballY=height/2;
        ballSpeedX=10.0;
        ballSpeedY=10.0;
    }

    public boolean updateBall(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;
        // next, see if the ball would meet some obstacle
        if (nextBallY < 0 || nextBallY > height) { 
            ballSpeedY = -ballSpeedY;
            nextBallY = ballY + deltaT * ballSpeedY;
        }

        if (nextBallX < 0 || nextBallX > width){
            ballSpeedX = -ballSpeedX;
            nextBallX = ballY + deltaT * ballSpeedY;
        }
        ballX = nextBallX;
        ballY = nextBallY;
        return false;
    }




}