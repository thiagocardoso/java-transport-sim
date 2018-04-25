import com.exercise.business.Manager;
import com.exercise.entity.Person;
import com.exercise.entity.State;
import com.exercise.travel.Place;
import com.google.common.base.Stopwatch;

import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class App {

    final Stopwatch stopWatch;

    App() {
        this.stopWatch = Stopwatch.createUnstarted();
    }

    public void header() {
        System.out.println("*********************************************");
        System.out.println("*           TRANSPORT SIMULATOR             *");
        System.out.println("*********************************************");
    }

    public void body() {
        State initial = createState(Place.AIRPORT);
        State finalState = createState(Place.PLANE);

        Manager manager = Manager.withStates(initial, finalState);
        manager.firstSolution();
    }

    public void startTimer() {
        this.stopWatch.start();
    }

    public void stopTimer() {
        this.stopWatch.stop();
        System.out.println("Elapsed Time: " + this.stopWatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
    }

    private State createState(Place location) {
        State state = State.of(location);
        state.addPassenger(location, Person.newPilot());
        state.addPassenger(location, Person.newOfficer());
        state.addPassenger(location, Person.newOfficer());

        state.addPassenger(location, Person.newOnboardChief());
        state.addPassenger(location, Person.newFlightAttendant());
        state.addPassenger(location, Person.newFlightAttendant());

        state.addPassenger(location, Person.newPoliceman());
        state.addPassenger(location, Person.newPrisoner());
        return state;
    }

    public static void main(String[] args) {
        App app = new App();
        app.header();
        app.startTimer();
        app.body();
        app.stopTimer();
    }
}
