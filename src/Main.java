import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Main {

    public static final char OPEN = '(';
    public static final char CLOSE = ')';


    public static void main(String[] args) {
        String test;
        // Примеры находяться в файле ./forTest.txt, используется try-with-recurse для того чтобы быть уверенным
        // что ресурс будет закрыт в случае ошибки или в конце исполнения
        try(BufferedReader br = new BufferedReader(new FileReader("./forTest.txt"))) {
            while ((test = br.readLine()) != null){
                if(Pattern.matches("[()]*\\(+[()]*|[()]*\\)+[()]*", test)){
                    algorithm(test);
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void algorithm(String test) {
        // TreeMap используется для хранения символов которые войдут в итоговую строку, в качестве ключа исползуется индекс символа в исходой строке
        // и за счёт сортировки от большего к меньшему мы получаем мы получим корректную последовательность символов
        TreeMap<Integer, Character> resultString = new TreeMap<>();
        // стэк хранит в себе индесы открывающих скобок, которым ещё не нашли пару
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < test.length(); i++){
            char symbol = test.charAt(i);
            // в случае нахождения открывающей скобки просто добавляем её индекс в стэк
            if(symbol == OPEN){
                stack.push(i);
            }
            // в случае нахождения закрывающей скобки и наличия для неё открывающей, убираем верхний элемент из стека и используем его в качстве ключа для TreeMap
            // для открывающей скобки, в качестве ключа для закрывающей скобки выступает индекс данной итрации
            // в случае если открывающей скобки нет, то просто пропускаем её
            // так как TreeMap сортирует элементы в порядке возрастания индекса мы можем быть уверены что скобки выстроятся корректно
            if(symbol == CLOSE){
                if(!stack.isEmpty()){
                    resultString.put(stack.pop(), OPEN);
                    resultString.put(i, CLOSE);
                }
            }
        }

        // в конце алгоритма у нас остаётся отсортированная по индексам TreeMap в которую вошлитолько те скобки у которых есть пара
        System.out.print(resultString.size() + " - ");
        resultString.forEach((k, v) -> System.out.print(v));
        System.out.println();
    }
}
