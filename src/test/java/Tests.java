import car.Car;
import car.CarState;
import car.SeatBeltLight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Tests {

    Car movingCar;
    Car staticCar;

    @BeforeEach
    public void setCar() {
        movingCar = new Car();
        movingCar.setCarSpeed(1020);
        movingCar.setCarState(CarState.ON);
        movingCar.setSeatbeltStatus(true);

        staticCar = new Car();
        staticCar.setCarSpeed(0);
        staticCar.setCarState(CarState.OFF);
        staticCar.setSeatbeltStatus(false);
    }


    //Case: if carState = ON/Contact -> clusterLight = on
    @Test
    public void verifyInstrumentClusterLightOn() {

        Assertions.assertTrue(movingCar.getInstrumentLight(), "Instrument cluster light was off while the car state is: " +
                movingCar.getCarState());

        movingCar.setCarState(CarState.CONTACT);
        Assertions.assertTrue(movingCar.getInstrumentLight(), "Instrument cluster light was off while the car state is: " +
                movingCar.getCarState());
    }

    //Case: if carState changes from off to on-> clusterLight = on
    @Test
    public void verifyInstrumentClusterLightOffOn() {

        Assertions.assertFalse(staticCar.getInstrumentLight(), "Instrument cluster light was on while the car state is: " +
                staticCar.getCarState());

        staticCar.setCarState(CarState.ON);
        Assertions.assertTrue(staticCar.getInstrumentLight(), "Instrument cluster light was off while the car state is: " +
                staticCar.getCarState());

    }

    //Case: if carState changes from on to off-> clusterLight = off
    @Test
    public void verifyInstrumentClusterLightOnOff() {

        Assertions.assertTrue(movingCar.getInstrumentLight(), "Instrument cluster light was off while the car state is: " +
                movingCar.getCarState());

        movingCar.setCarState(CarState.OFF);
        Assertions.assertFalse(movingCar.getInstrumentLight(), "Instrument cluster light was on while the car state is: " +
                movingCar.getCarState());

//        Transition from off to contact
        staticCar.setCarState(CarState.CONTACT);
        Assertions.assertTrue(staticCar.getInstrumentLight(), "Instrument cluster light was off while the car state is: " +
                staticCar.getCarState());
    }

    //Case: if carState = Off -> clusterLight = off
    @Test
    public void verifyInstrumentClusterLightOff() {

        Assertions.assertFalse(staticCar.getInstrumentLight(), "Instrument cluster light was on while the car state is: " +
                staticCar.getCarState());
    }


    //Case: speedometer displays speed correctly
    @Test
    public void verifySpeedometer() {

        Assertions.assertEquals(movingCar.getCarSpeed() * 0.1,
                movingCar.getSpeedometer(),
                "The speedometer shows incorrect speed: " +
                        movingCar.getCarSpeed());

        movingCar.setCarSpeed(0);
        Assertions.assertEquals(movingCar.getCarSpeed() * 0.1,
                movingCar.getSpeedometer(),
                "The speedometer shows incorrect speed: " +
                        movingCar.getCarSpeed());

        movingCar.setCarSpeed(4098);
//        Assumed that for speed over the speed limit the speedometer displays always 250km/h, could be checked for an error as well
        Assertions.assertEquals(250,
                movingCar.getSpeedometer(),
                "The speedometer shows incorrect speed: " +
                        movingCar.getCarSpeed());

        movingCar.setCarSpeed(2555);
//        Assumed that for speed over the speed limit the speedometer displays always 250km/h, could be checked for an error as well
        Assertions.assertEquals(250,
                movingCar.getSpeedometer(),
                "The speedometer shows incorrect speed: " +
                        movingCar.getCarSpeed());
    }

    //    Case: the speedometer should display a warning when the speed is >247km/h
    @Test
    public void verifySpeedWarning() {
        movingCar.setCarSpeed(2500);
        Assertions.assertTrue(movingCar.getSpeedWarning(), "The speedometer doesn't show a warning when speed is: " +
                movingCar.getCarSpeed());

//        Checking transition -> when the speed decreases the warning should disappear
        movingCar.setCarSpeed(2470);
        Assertions.assertFalse(movingCar.getSpeedWarning(), "The speedometer shows a warning when speed is: " +
                movingCar.getCarSpeed());

//        Checking transition -> when the speed increases the warning should appear
        movingCar.setCarSpeed(2480);
        Assertions.assertTrue(movingCar.getSpeedWarning(), "The speedometer doesn't show a warning when speed is: " +
                movingCar.getCarSpeed());

    }

    // Case: if carState = off, seatbeltTelltale = off
    @Test
    public void verifySeatbeltTelltaleOffStoppedCar() {

        staticCar.setCarState(CarState.OFF);
        Assertions.assertEquals(SeatBeltLight.OFF,
                staticCar.getSeatBeltLight(),
                "The seatbelt telltale is on for a stopped car");

    }

    // Case: if carState = on/contact and seatbelt is unbuckled, seatbeltTelltale = on
    @Test
    public void verifySeatbeltTelltaleOn() {

        movingCar.setSeatbeltStatus(false);
        movingCar.setCarSpeed(0);
        movingCar.setCarState(CarState.CONTACT);
        Assertions.assertEquals(SeatBeltLight.ON,
                movingCar.getSeatBeltLight(),
                "The seatbelt telltale is not on for unbuckled belt in a car on contact");

        movingCar.setCarSpeed(240);
        movingCar.setCarState(CarState.ON);
        Assertions.assertEquals(SeatBeltLight.ON,
                movingCar.getSeatBeltLight(),
                "The seatbelt telltale is not on for unbuckled belt in a moving car");

        //     Check if the seatbelt telltale switches to blinking for speed=25km/h
        movingCar.setCarSpeed(250);
        Assertions.assertEquals(SeatBeltLight.BLINK,
                movingCar.getSeatBeltLight(),
                "The seatbelt telltale is not blinking for unbuckled belt in a moving car with speed 25");

        //     Check if the seatbelt telltale is blinking for speed>25km/h
        movingCar.setCarSpeed(260);
        Assertions.assertEquals(SeatBeltLight.BLINK,
                movingCar.getSeatBeltLight(),
                "The seatbelt telltale is not blinking for unbuckled belt in a moving car with speed 26");

        //     Check if the seatbelt telltale switches to on for speed<25km/h
        movingCar.setCarSpeed(240);
        movingCar.setCarState(CarState.ON);
        Assertions.assertEquals(SeatBeltLight.ON,
                movingCar.getSeatBeltLight(),
                "The seatbelt telltale is not on for unbuckled belt in a moving car");
    }


    // Case: if carState = on/contact and seatbelt is buckled, seatbeltTelltale = off
    @Test
    public void verifySeatbeltTelltaleOff() {

        movingCar.setSeatbeltStatus(true);
        movingCar.setCarSpeed(0);
        movingCar.setCarState(CarState.CONTACT);
        Assertions.assertEquals(SeatBeltLight.OFF,
                movingCar.getSeatBeltLight(),
                "The seatbelt telltale is " + movingCar.getSeatBeltLight() +" for buckled belt in a car on contact");

//       Check if the telltale remains off for buckled seatbelt and different speed
        movingCar.setCarSpeed(240);
        movingCar.setCarState(CarState.ON);
        Assertions.assertEquals(SeatBeltLight.OFF,
                movingCar.getSeatBeltLight(),
                "The seatbelt telltale is " + movingCar.getSeatBeltLight() + " for buckled belt in a moving car");

        movingCar.setCarSpeed(250);
        Assertions.assertEquals(SeatBeltLight.OFF,
                movingCar.getSeatBeltLight(),
                "The seatbelt telltale is " + movingCar.getSeatBeltLight() +" for buckled belt in a moving car with speed 25km/h");

        movingCar.setCarSpeed(260);
        Assertions.assertEquals(SeatBeltLight.OFF,
                movingCar.getSeatBeltLight(),
                "The seatbelt telltale is " + movingCar.getSeatBeltLight() +" for buckled belt in a moving car with speed 26km/h");

        movingCar.setCarSpeed(240);
        movingCar.setCarState(CarState.ON);
        Assertions.assertEquals(SeatBeltLight.OFF,
                movingCar.getSeatBeltLight(),
                "The seatbelt telltale is " + movingCar.getSeatBeltLight() +" for buckled belt in a moving car");
    }
}



