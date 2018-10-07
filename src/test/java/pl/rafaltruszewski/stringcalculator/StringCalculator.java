package pl.rafaltruszewski.stringcalculator;

class StringCalculator {

    public static final String DEFAULT_DELIMITER = "[,\n]";

    public int add(String numbers) {
        if(numbers.isEmpty()) return 0;

        String delimiter = DEFAULT_DELIMITER;

        String numbersWithoutDelimiter;

        if (hasDelimiter(numbers)){
            delimiter = extractDelimiter(numbers);
            numbersWithoutDelimiter = removeDelimiter(delimiter, numbers);
        }
        else {
            numbersWithoutDelimiter = numbers;
        }
        String[] numbersAsTab = numbersWithoutDelimiter.split(delimiter);

        int sum = 0;
        for (String number : numbersAsTab) {
            sum += Integer.valueOf(number);
        }
        return sum;
    }

    private String removeDelimiter(String delimiter, String numbers) {
        String numbersWithoutDelimiter = numbers.substring(2 + delimiter.length());
        if(numbersWithoutDelimiter.startsWith("\n")){
          numbersWithoutDelimiter = numbersWithoutDelimiter.substring(1);
        }

        return numbersWithoutDelimiter;
    }

    private String extractDelimiter(String value) {
        return value.substring(2, 3);
    }

    private boolean hasDelimiter(String value) {
        return value.startsWith("//");
    }
}
