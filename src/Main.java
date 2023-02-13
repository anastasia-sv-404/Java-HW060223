import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        HashSet<Laptop> laptopsBase = getLaptopBase(10);
        System.out.println("Модели в наличии: ");
        for (var item : laptopsBase) {
            System.out.println(item);
        }

        System.out.println();
        HashMap<Integer, Object> forSearch = new HashMap<>();

        boolean choise = true;
        while (choise) {
            int criteria = checkCriteria();
            if (criteria == 2 || criteria == 3) {
                int value = checkIntValue();
                forSearch.put(criteria, value);
            } else {
                String value = checkStringValue(criteria);
                forSearch.put(criteria, value);
            }
            System.out.println();
            if (anotherAction() != 1) {
                HashSet<Laptop> searchingSet = getSearchingLaptops(forSearch, laptopsBase);
                System.out.println("\nВы ввели следующие параметры для поиска: " + forSearch);
                if (searchingSet.isEmpty()) {
                    System.out.println("\nМы не нашли подходящих моделей. Попробуйте изменить параметры поиска.");
                } else {
                    System.out.println("\nМы нашли следующие модели по введенным Вами параметрам: ");
                    for (var laptop : searchingSet) {
                        System.out.println(laptop);
                    }
                }
                choise = false;
            }
        }
    }

    static String readFile(String filePath) {
        File file = new File(filePath);
        StringBuilder sb = new StringBuilder();
        try (Scanner sc = new Scanner(file)) {
            sb.append(sc.nextLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }//Чтение "баз данных" с вариантами брендов, цветов, ОС.

    static Laptop getExemplar() {
        String[] brands = readFile("brandsDB.txt").split(",");
        int[] ram = new int[]{2, 3, 4, 8, 16, 32, 64};
        int[] hdd = new int[]{500, 1000, 2000, 8000, 16000};
        String[] os = readFile("osDB.txt").split(",");
        String[] color = readFile("colorDB.txt").split(",");

        Random random = new Random();
        Laptop laptop = new Laptop();

        laptop.brand = brands[random.nextInt(brands.length)];
        laptop.ram = ram[random.nextInt(ram.length)];
        laptop.hdd = hdd[random.nextInt(hdd.length)];
        laptop.os = os[random.nextInt(os.length)];
        laptop.color = color[random.nextInt(color.length)];
        laptop.serialNum = random.nextInt(1000000);

        return laptop;
    }//Создание условно рандомного экземпляра класса Laptop.

    static HashSet<Laptop> getLaptopBase(int maxCount) {
        HashSet<Laptop> laptopsBase = new HashSet<>();
        for (int i = 0; i < maxCount; i++) {
            laptopsBase.add(getExemplar());
        }
        return laptopsBase;
    }// Создание заданного количества экземпляров класса Laptop.
    // Используя коллекцию Set + переопределив хэши -> дополнительно обеспечиваем уникальность каждого
    // экземпляра класса в базе магазина.
    //В дополнение к генерации случайного серийного номера на уровне экземпляра.

    static int checkCriteria() {
        while (true) {
            Scanner firstScanner = new Scanner(System.in);
            System.out.print("\nВведите цифру, соответствующую необходимому критерию:\n" +
                    "1 - Бренд\n" +
                    "2 - ОЗУ\n" +
                    "3 - Объем ЖД\n" +
                    "4 - Операционная система\n" +
                    "5 - Цвет\n");
            boolean flag = firstScanner.hasNextInt();
            if (!flag) {
                System.out.println("Вы ввели неверное значение. Попробуйте еще раз.");
            } else {
                int num = firstScanner.nextInt();
                if (num < 1 || num > 5) {
                    System.out.println("Введите один из параметров, указанных в меню.");
                } else {
                    return num;
                }
            }
        }
    }//Меню для выбора параметров поиска.

    static int checkIntValue() {
        while (true) {
            Scanner secondScanner = new Scanner(System.in);
            System.out.print("Введите минимальное значение для выбранного Вами критерия (в ГБ): ");
            boolean flag = secondScanner.hasNextInt();
            if (!flag) {
                System.out.println("Вы ввели неверное значение. Попробуйте еще раз.");
            } else {
                return secondScanner.nextInt();
            }
        }
    }//Ввод минимальных значений для числовых параметров поиска.

    static String checkStringValue(int criteria) {
        String value = " ";
        String[] brands = readFile("brandsDB.txt").split(",");
        String[] os = readFile("osDB.txt").split(",");
        String[] color = readFile("colorDB.txt").split(",");
        while (true) {
            Scanner thirdScanner = new Scanner(System.in);
            if (criteria == 1) {
                System.out.println("Введите бренд ноутбука. В наличии есть: " + Arrays.toString(brands));
                value = thirdScanner.nextLine();
                if (Arrays.asList(brands).contains(value)) {
                    return value;
                } else {
                    System.out.println("Пожалуйста, выберете бренд, который есть в наличии.");
                }
            } else if (criteria == 4) {
                System.out.println("Введите ОС ноутбука. В наличии есть: " + Arrays.toString(os));
                value = thirdScanner.nextLine();
                if (Arrays.asList(os).contains(value)) {
                    return value;
                } else {
                    System.out.println("Пожалуйста, выберете ОС, которая есть в наличии.");
                }
            } else {
                System.out.println("Введите цвет ноутбука. В наличии есть: " + Arrays.toString(color));
                value = thirdScanner.nextLine();
                if (Arrays.asList(color).contains(value)) {
                    return value;
                } else {
                    System.out.println("Пожалуйста, выберете цвет, который есть в наличии.");
                }
            }
        }
    }//Ввод значений для текстовых параметров поиска.

    static int anotherAction() {
        while (true) {
            Scanner forthScanner = new Scanner(System.in);
            System.out.print("Желаете ввести еще параметр для поиска или изменить введенные ранее параметры?" +
                    " (1 - да, 2 - нет): ");
            if (!forthScanner.hasNextInt()) {
                System.out.println("Вы ввели неверное значение. Попробуйте еще раз.");
            } else {
                return forthScanner.nextInt();
            }
        }
    }//Запрос следующего параметра для поиска (возврат в меню).

    static HashSet<Laptop> getSearchingLaptops(HashMap<Integer, Object> forSearch, HashSet<Laptop> laptopsBase) {
        HashSet<Laptop> searchingSet = new HashSet<>();
        boolean check = true;

        for (var laptop : laptopsBase) {
            boolean choiseFirst = true;
            boolean choiseSecond = true;
            boolean choiseThird = true;
            boolean choiseForth = true;
            boolean choiseFifth = true;
            for (var item : forSearch.entrySet()) {
                if (item.getKey() == 1) {
                    choiseFirst = laptop.brand.equals(item.getValue());
                } else if (item.getKey() == 2) {
                    if (laptop.ram < (Integer) item.getValue()) {
                        choiseSecond = false;
                    }
                } else if (item.getKey() == 3) {
                    if (laptop.hdd < (Integer) item.getValue()) {
                        choiseThird = false;
                    }
                } else if (item.getKey() == 4) {
                    choiseForth = laptop.os.equals(item.getValue());
                } else {
                    choiseFifth = laptop.color.equals(item.getValue());
                }
            }
            if (choiseFirst && choiseSecond && choiseThird && choiseForth && choiseFifth && check) {
                searchingSet.add(laptop);
            }
        }
        return searchingSet;
    }// Поиск ноутбуков в базе по заданным параметрам поиска.
}
