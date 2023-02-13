package by.sologub;

import by.sologub.model.Animal;
import by.sologub.model.Car;
import by.sologub.model.Flower;
import by.sologub.model.House;
import by.sologub.model.Person;
import by.sologub.util.Util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
//        task1();
//        task2();
//        task3();
//        task4();
//        task5();
//        task6();
//        task7();
//        task8();
//        task9();
//        task10();
//        task11();
//        task12();
//        task13();
//        task14();
//        task15();
        task16();
    }

    private static void task1() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 9 && animal.getAge() < 21)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(14)
                .limit(7)
                .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> "Japanese".equals(animal.getOrigin()))
                .peek(animal -> {
                    if ("Female".equals(animal.getGender())){
                        animal.setBread(animal.getBread().toUpperCase());
                    }
                })
                .map(Animal::getBread)
                .forEach(System.out::println);
    }

    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 29)
                .map(Animal::getOrigin)
                .distinct()
                .filter(s -> s.charAt(0) == 'A')
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        List<Animal> animals = Util.getAnimals();
        Long count = animals.stream()
                .filter(animal -> "Female".equals(animal.getGender()))
                .count();
        System.out.println(count);
    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean isHungarianExist = animals.stream()
                .filter(animal -> animal.getAge() > 19 && animal.getAge() < 31)
                .anyMatch(animal ->"Hungarian".equals(animal.getOrigin()));
        System.out.println(isHungarianExist);
    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean isGender = animals.stream()
                .allMatch(animal -> "Female".equals(animal.getGender()) || "Male".equals(animal.getGender()));
        System.out.println(isGender);
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean isOceania = animals.stream()
                .noneMatch(animal -> "Oceania".equals(animal.getOrigin()));
        System.out.println(isOceania);
    }

    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(99)
                .forEach(s -> System.out.println(s.getAge()));
    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
                animals.stream()
                        .map(Animal::getBread)
                        .map(String::toCharArray)
                        .sorted(Comparator.comparingInt(s -> s.length))
                        .limit(1)
                        .forEach(s -> System.out.println(s.length));
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
        int age = animals.stream()
                        .mapToInt(Animal::getAge).sum();
        System.out.println(age);
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
        double average = animals.stream()
                .filter(animal -> "Indonesian".equals(animal.getOrigin()))
                .mapToInt(Animal::getAge).summaryStatistics().getAverage();
        System.out.println(average);
    }

    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();
        people.stream()
                .filter(person -> "Male".equals(person.getGender())
                        && yearDiff(person.getDateOfBirth()) > 17
                        && yearDiff(person.getDateOfBirth()) < 28)
                .sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();

        Stream<Person> personHospital = houses.stream().filter(house -> "Hospital".equals(house.getBuildingType()))
                .flatMap(house -> house.getPersonList().stream());

        Stream<Person> people = houses.stream()
                .filter(house -> !"Hospital".equals(house.getBuildingType()))
                .flatMap(house -> house.getPersonList().stream())
                .sorted((o1, o2) -> {
                    if (yearDiff(o1.getDateOfBirth()) < 19
                            || yearDiff(o1.getDateOfBirth()) > 59) {
                        return -1;
                    }
                    if (yearDiff(o2.getDateOfBirth()) < 19
                            || yearDiff(o2.getDateOfBirth()) > 59) {
                        return 1;
                    }
                    return 0;
                });
        Stream.concat(personHospital, people).limit(500).forEach(System.out::println);
    }

    private static void task14() throws IOException {
        List<Car> cars = Util.getCars();
        AtomicLong totalMass = new AtomicLong();

        AtomicLong turkmenistanMass = new AtomicLong();
        List<Car> turkmenistan = cars.stream()
                .filter(car -> "Jaguar".equals(car.getCarMake()) || "White".equals(car.getColor()))
                .peek(car -> turkmenistanMass.addAndGet(car.getMass()))
                .peek(car -> totalMass.addAndGet(car.getMass()))
                .toList();
        cars.removeAll(turkmenistan);

        AtomicLong uzbekistanMass = new AtomicLong();
        List<Car> uzbekistan = cars.stream()
                .filter(car -> car.getMass() < 1500
                        || "BMW" .equals(car.getCarMake())
                        || "Chrysler".equals(car.getCarMake())
                        || "Toyota".equals(car.getCarMake())
                        || "Lexus".equals(car.getCarMake()))
                .peek(car -> uzbekistanMass.addAndGet(car.getMass()))
                .peek(car -> totalMass.addAndGet(car.getMass()))
                .toList();
        cars.removeAll(uzbekistan);

        AtomicLong kazakhstanMass = new AtomicLong();
        List<Car> kazakhstan = cars.stream()
                .filter(car -> ("Black".equals(car.getColor()) && car.getMass() > 4000)
                        || "GMC".equals(car.getCarMake())
                        || "Dodge".equals(car.getCarMake()))
                .peek(car -> kazakhstanMass.addAndGet(car.getMass()))
                .peek(car -> totalMass.addAndGet(car.getMass()))
                .toList();
        cars.removeAll(kazakhstan);

        AtomicLong kyrgyzstanMass = new AtomicLong();
        List<Car> kyrgyzstan = cars.stream()
                .filter(car -> car.getReleaseYear() < 1982
                        || "Civic".equals(car.getCarModel())
                        || "Cherokee".equals(car.getCarModel()))
                .peek(car -> kyrgyzstanMass.addAndGet(car.getMass()))
                .peek(car -> totalMass.addAndGet(car.getMass()))
                .toList();
        cars.removeAll(kyrgyzstan);

        AtomicLong russiaMass = new AtomicLong();
        List<Car> russia = cars.stream()
                .filter(car -> (!"Yellow".equals(car.getColor())
                        && !"Red".equals(car.getColor())
                        && !"Green".equals(car.getColor())
                        && !"Blue".equals(car.getColor()))
                        || car.getPrice() > 40000)
                .peek(car -> russiaMass.addAndGet(car.getMass()))
                .peek(car -> totalMass.addAndGet(car.getMass()))
                .toList();
        cars.removeAll(russia);

        AtomicLong mongoliaMass = new AtomicLong();
        List<Car> mongolia = cars.stream()
                .filter(car -> car.getVin().contains("59"))
                .peek(car -> mongoliaMass.addAndGet(car.getMass()))
                .peek(car -> totalMass.addAndGet(car.getMass()))
                .toList();
        System.out.println("Total income= " + totalMass.get() * 7.14 / 1000 + "$");
        System.out.println("Uzbekistan totalMass car mass= " + uzbekistanMass + ", expenses= " + uzbekistanMass.get() * 7.14 / 1000);
        System.out.println("Turkmenistan totalMass car mass= " + turkmenistanMass + ", expenses= " + turkmenistanMass.get() * 7.14 / 1000);
        System.out.println("Kazakhstan totalMass car mass= " + kazakhstanMass + ", expenses= " + kazakhstanMass.get() * 7.14 / 1000);
        System.out.println("Kyrgyzstan totalMass car mass= " + kyrgyzstanMass + ", expenses= " + kyrgyzstanMass.get() * 7.14 / 1000);
        System.out.println("Russia totalMass car mass= " + russiaMass + ", expenses= " + russiaMass.get() * 7.14 / 1000);
        System.out.println("Mongolia totalMass car mass= " + mongoliaMass + ", expenses= " + mongoliaMass.get() * 7.14 / 1000);
    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        double sum = flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin)
                        .reversed()
                        .thenComparing(Flower::getPrice)
                        .thenComparing(Flower::getWaterConsumptionPerDay)
                        .reversed()
                )
                .filter(flower -> String.valueOf(flower.getCommonName().charAt(0)).matches("[C-S]"))
                .filter(Flower::isShadePreferred)
                .filter(flower -> flower.getFlowerVaseMaterial().contains("Glass")
                        || flower.getFlowerVaseMaterial().contains("Aluminium")
                        || flower.getFlowerVaseMaterial().contains("Steel"))
                .mapToDouble(flower -> BigDecimal.valueOf(flower.getPrice())
                        .add(BigDecimal.valueOf(flower.getWaterConsumptionPerDay())
                                .multiply(BigDecimal.valueOf(5 * 365 + 1))
                                .multiply(BigDecimal.valueOf(1.39))
                                .divide(BigDecimal.valueOf(1000), 6, RoundingMode.HALF_UP)
                        )
                        .doubleValue())
                .sum();
        System.out.println(sum + "$");
    }

    private static void task16() throws IOException{
        List<Person> people = Util.getPersons();
        double average = people.stream()
                .filter(person -> person.getRecruitmentGroup() > 2)
                .filter(person -> person.getCity().charAt(0) != 'S')
                .sorted(Comparator.comparing(Person::getDateOfBirth).reversed())
                .limit(100)
                .peek(person -> person.setOccupation("pensioner"))
                .peek(Main::sendEmail)
                .mapToLong(person -> ChronoUnit.DAYS.between(person.getDateOfBirth(), LocalDate.now()) * 5)
                .summaryStatistics().getAverage();
        System.out.println(average);
    }

    private static long yearDiff(LocalDate date){
        return ChronoUnit.YEARS.between(date, LocalDate.now());
    }

    private static void sendEmail(Person person){
        long payout = ChronoUnit.DAYS.between(person.getDateOfBirth(), LocalDate.now()) * 5;
        System.out.println(person.getFirstName() + ", your payout is " + payout);
    }
}