package car;

public class Car {
    private CarState carState;
    private double carSpeed;
    private boolean seatbeltStatus;
    private int speedLimit;
    private boolean speedWarning;
    private boolean instrumentClusterLight;
    private SeatBeltLight seatBeltLight;


    public Car() {
        carState = CarState.OFF;
        carSpeed = 0;
        seatbeltStatus = false;
        speedLimit = 250;
        speedWarning = false;
        instrumentClusterLight = false;
        seatBeltLight = SeatBeltLight.OFF;
    }

    public void setCarState(CarState carState) {
        this.carState = carState;
        if (carState.equals(CarState.ON) || carState.equals(CarState.CONTACT)) {
            instrumentClusterLight = true;
        } else {
            instrumentClusterLight = false;
            seatBeltLight = SeatBeltLight.OFF;
        }
    }

    public CarState getCarState() {
        return carState;
    }

    public void setCarSpeed(double carSpeed) {
        if (carSpeed >= 0 && carSpeed <= 4098) {
            this.carSpeed = carSpeed;
            if (getSpeedometer() >= speedLimit - 2) {
                speedWarning = true;
            } else {
                speedWarning = false;
            }
        } else {
            System.out.println(carSpeed + " is invalid speed");
        }

        if (!seatbeltStatus) {
            if (this.carSpeed < 250) {
                seatBeltLight = SeatBeltLight.ON;
            } else {
                seatBeltLight = SeatBeltLight.BLINK;
            }
        }
    }

    public double getCarSpeed() {
        return carSpeed;
    }

    public void setSeatbeltStatus(boolean seatbeltStatus) {
        this.seatbeltStatus = seatbeltStatus;
        if (!this.seatbeltStatus) {
            if (carSpeed > 0) {
                seatBeltLight = SeatBeltLight.ON;
            }
            if (carSpeed > 250) {
                seatBeltLight = SeatBeltLight.BLINK;
            }
        } else {
            seatBeltLight = SeatBeltLight.OFF;
        }
    }

    public boolean getSeatBeltStatus() {
        return seatbeltStatus;
    }

    public boolean getSpeedWarning() {
        return speedWarning;
    }

    public int getSpeedometer() {
        if (carSpeed * 0.1 < 250) {
            return (int) (carSpeed * 0.1);
        } else {
            return 250;
        }
    }

    public boolean getInstrumentLight() {
        return instrumentClusterLight;
    }

    public SeatBeltLight getSeatBeltLight() {
        return seatBeltLight;
    }
}
