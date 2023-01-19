import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strs = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().equals("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    strs.add(parameters[1]);
                    return String.format("%s added to search engine", parameters[1]);
                }
            }
            if(url.getPath().equals("/search")) {
                String[] parameters = url.getQuery().split("=");
                String res = "";
                if (parameters[0].equals("s")) {
                    for(String str : strs) {
                        if(str.contains(parameters[1]))
                            res += str + ", ";
                    }
                    res = res.substring(0, res.length() - 1);
                    return String.format("%s", res);
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
