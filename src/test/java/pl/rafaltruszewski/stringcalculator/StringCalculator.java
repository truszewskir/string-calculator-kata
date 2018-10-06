package pl.rafaltruszewski.stringcalculator;

class StringCalculator {

    public int add(String value) {
        if(value.isEmpty()) return 0;
        String[] numbers = value.split(",");

        int sum = 0;
        for (String number : numbers) {
            sum += Integer.valueOf(number);
        }
        return sum;
    }
}
