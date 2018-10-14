package pl.rafaltruszewski.stringcalculator;

import java.util.List;
import java.util.stream.Collectors;

class StringCalculator {

    private final NumbersParser numbersParser;

    StringCalculator(NumbersParser numbersParser) {
        this.numbersParser = numbersParser;
    }

    public int add(String numbers) throws NegativesNotAllowed {
        if(numbers.isEmpty()) return 0;

        List<Integer> numbersAsInt = numbersParser.parse(numbers);
        checkNegatives(numbersAsInt);

        return sum(numbersAsInt);
    }

    private int sum(List<Integer> numbersAsInt) {
        return numbersAsInt.stream()
                .filter(e -> e <= 1000)
                .mapToInt(Integer::valueOf)
                .sum();
    }

    private void checkNegatives(List<Integer> numbersAsInt) {
        List<Integer> negatives = numbersAsInt.stream()
                .filter(e -> e < 0)
                .collect(Collectors.toList());


        if(negatives.isEmpty()){
            return;
        }

        StringBuilder message = new StringBuilder();
        message.append(negatives.get(0));

        for (int i = 1; i < negatives.size(); i++){
            message.append(",")
                    .append(negatives.get(i));
        }

        throw new NegativesNotAllowed(message.toString());
    }

}
