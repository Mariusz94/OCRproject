import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Program do pobierania informacji ze strony iternetowej o pokemoach
 */
public class ParseHTMl {
    public static void main(String[] args) {
        String Url = "https://bulbapedia.bulbagarden.net/wiki/List_of_Pokémon_by_National_Pokédex_number";
        int connectionTimeoutMs = 10000; //10s

        Document document;

        Map<String, PokemonModel> pokedex = new LinkedHashMap<>();

        try {
            document = Jsoup.parse(new URL(Url), connectionTimeoutMs);

            Elements allGeneration = document.getElementById("mw-content-text").getElementsByTag("h3");
            int generationCounter = 1;
            for (Element generation : allGeneration) {
                int count = 0;
                for (Element row : generation.nextElementSiblings().first().children().first().children()) {
                    if (count == 0) {
                        count++;
                        continue;
                    }
                    if (row.children().get(1).text().contains("?")) break;
                    PokemonModel pokemonModel = new PokemonModel();
                    //index
                    pokemonModel.setIndex(Integer.parseInt(row.children().get(1).text().replace("#","")));
                    //name
                    pokemonModel.setName(row.children().get(3).children().first().text());
                    //generation
                    pokemonModel.setGeneration(generationCounter);
                    //type
                    List<String> type = new LinkedList<>();
                    type.add(row.children().get(4).children().first().children().first().html());
                    if (row.children().get(4).nextElementSibling() != null)
                        type.add(row.children().get(5).children().first().children().first().html());
                     pokemonModel.setType(type);
                     pokedex.put(pokemonModel.getName(),pokemonModel);
                }
                generationCounter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        PokemonService.getInstance().setPokedex(pokedex);


        for (Map.Entry<String, PokemonModel> entry : PokemonService.getInstance().getPokedex().entrySet()) {
            try {
                document = Jsoup.parse(new URL("https://bulbapedia.bulbagarden.net/wiki/"+entry.getValue().getName() +"_(Pokémon)"), connectionTimeoutMs);
                System.out.println("https://bulbapedia.bulbagarden.net/wiki/"+entry.getValue().getName() +"_(Pokémon)");
                System.out.println(entry.getValue().getIndex());

                entry.getValue().setColorText(document.select("td:contains(Pokédex color)").first().child(1).child(0).child(0).child(0).text().substring(2));
                entry.getValue().setColor(document.select("td:contains(Pokédex color)").first().child(1).child(0).child(0).child(0).html().substring(24, 31));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        PokemonService.getInstance().saveListPokemonToFile();

    }
}
// https://bulbapedia.bulbagarden.net/wiki/Ditto_(Pokémon)