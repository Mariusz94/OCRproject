package pokemonGame;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class PokemonService {
    private Map<String, PokemonModel> pokedex;

    private static PokemonService instance = new PokemonService();

    private PokemonService() {
    }

    public Map<String, PokemonModel> getPokedex() {
        return pokedex;
    }

    public void setPokedex(Map<String, PokemonModel> pokedex) {
        this.pokedex = pokedex;
    }

    public static PokemonService getInstance(){
        return instance;
    }

    public void saveListPokemonToFile(){
        File file = new File("src\\main\\resources\\pokedex.txt");
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
            printWriter.println(pokedex.size());
            for (Map.Entry<String, PokemonModel> entry : pokedex.entrySet()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(entry.getValue().getIndex());
                stringBuilder.append("|");
                stringBuilder.append(entry.getValue().getName());
                stringBuilder.append("|");
                stringBuilder.append(entry.getValue().getGeneration());
                stringBuilder.append("|");
                for (String s : entry.getValue().getType()) {
                    stringBuilder.append(s);
                    if(entry.getValue().getType().size()>1)
                        stringBuilder.append("/");
                }
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
                System.out.println(stringBuilder.toString());
                stringBuilder.append("|");
                stringBuilder.append(entry.getValue().getColor().getRed());
                stringBuilder.append("|");
                stringBuilder.append(entry.getValue().getColor().getGreen());
                stringBuilder.append("|");
                stringBuilder.append(entry.getValue().getColor().getBlue());
                stringBuilder.append("|");
                stringBuilder.append(entry.getValue().getColorText());
                printWriter.println(stringBuilder.toString());

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            printWriter.close();
        }
    }

    public void readListPokemonFromFile(){
        FileReader fileToRead = null;
        try {
            fileToRead = new FileReader("src\\main\\resources\\pokedex.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(fileToRead);
        int numberOfRecords = 0;
        try {
            numberOfRecords = Integer.parseInt(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        PokemonModel[] pokemonArray = new PokemonModel[numberOfRecords];

        for (int i = 0; i < numberOfRecords; i++) {
            String line = null;
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringTokenizer stringTokenizer = new StringTokenizer(line,"|");
            pokemonArray[i] = new PokemonModel();
            pokemonArray[i].setIndex(Integer.parseInt(stringTokenizer.nextToken()));
            pokemonArray[i].setName(stringTokenizer.nextToken());
            pokemonArray[i].setGeneration(Integer.parseInt(stringTokenizer.nextToken()));
            String[] type = stringTokenizer.nextToken().split("/");
            List<String> typeList = new LinkedList<>();
            for (String s : type) {
                typeList.add(s);
            }
            pokemonArray[i].setType(typeList);
            pokemonArray[i].setColor(new Color(Integer.parseInt(stringTokenizer.nextToken()),
                    Integer.parseInt(stringTokenizer.nextToken()),
                    Integer.parseInt(stringTokenizer.nextToken())));
            pokemonArray[i].setColorText(stringTokenizer.nextToken());
        }
        try {
            fileToRead.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pokedex = new LinkedHashMap<>();
        for (PokemonModel pokemonModel : pokemonArray) {
            pokedex.put(pokemonModel.getName(),pokemonModel);
        }

    }
}
