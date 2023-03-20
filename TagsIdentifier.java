package HTMLtags;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class TagsIdentifier {
    
    Stack<String> tags = new Stack<String>();
    final String[] NO_CLOSE_TAGS = new String[] {"<area>", "<base>", "<br>", "<col>", "<command>", "<embed>", "<hr>", "<img>", "<input>", "<keygen>",
                                                "<link>", "<meta>", "<param>", "<source>", "<track>", "<wbr>"};
    String not_closed;
    String file_name;

    TagsIdentifier(String file_name) {
        this.file_name = file_name;
    }

    public static boolean compareTags(String open_tag, String close_tag) {
        /**
         * Used to compare two tags to see if the second doesn't close the first. Returns false if it does.
         */
        open_tag = open_tag.substring(1, open_tag.length() - 1);
        String tmp = "";
        for (int i = 0; i < open_tag.length(); i++) {
            if (open_tag.charAt(i) != ' ') tmp += open_tag.charAt(i);
            else break;
        }
        open_tag = tmp;
        close_tag = close_tag.substring(2, close_tag.length() - 1);
        if (open_tag.equals(close_tag)) return false;
        else return true;
    }

    private boolean identifyTags() throws FileNotFoundException {
        Scanner file = new Scanner(new FileReader(this.file_name));
        while (file.hasNext()) {
            String line = file.nextLine();
            String tag = "";
            boolean append = false;
            
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '<') {
                    tag += line.charAt(i);
                    append = true;
                } else if (append) {
                    if (line.charAt(i) == '>') {
                        tag += '>';
                        append = false;
                    }
                    else {
                        tag += line.charAt(i);
                    }
                }

                if (tag.length() > 1 && tag.charAt(tag.length() - 1) == '>') {
                    boolean compare;
                    if (tag.charAt(1) != '/') {
                        compare = true;
                        if (tag.charAt(1) == '!') compare = false;
                        else{
                            String tmp = "";
                            // System.out.println(tag);
                            for (int j = 0; j < tag.length(); j++) {
                                if (tag.charAt(j) != ' ') tmp += tag.charAt(j);
                                else {
                                    tmp += '>';
                                    break;
                                }
                            }
                            for (String t : NO_CLOSE_TAGS) {
                                if (tmp.equals(t)) compare = false;
                            }
                        }
                        if (compare) tags.push(tag);
                    } else {
                        String o_tag = tags.pop();
                        compare = compareTags(o_tag, tag);
                        if (compare) {
                            not_closed = o_tag;
                            return false;
                        }
                    }
                    tag = "";
                }
            }
            if (tag.length() > 1) {
                not_closed = tag;
                return false;
            }
        }
        file.close();
        if (tags.isEmpty()) return true;
        else {
            Set<String> unique = new HashSet<String>(tags);
            not_closed = unique.toString();
            if (unique.size() != tags.size()) {
                not_closed += "\nYou may have opened those tags again instead of closing them.";
            }
            return false;
        }
    }

    @Override
    public String toString() {
        /**
         * Returns a string telling if all tags are closed or if there are still open tags.
         */
        try {
            boolean x = identifyTags();
            if (x) return "All tags are closed";
            else return "Not closed: " + not_closed;
        } catch (FileNotFoundException e) {
            return "The file name you entered is not found";
        }
    }

}
