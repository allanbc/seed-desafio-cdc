package br.com.deveficiente.casadocodigov2.util;

import com.github.ladutsko.isbn.ISBN;
import com.github.ladutsko.isbn.ISBNException;
import com.github.ladutsko.isbn.ISBNFormat;

import java.util.Random;

public class GeraIsbn {
    public static void main(String[] args) {
        try {
            Random random = new Random(131872486);
            ISBN isbn = ISBN.parseIsbn(random.toString()); // or 978-0131872486
            // Valid isbn string
            ISBNFormat format = new ISBNFormat();
            System.out.println(format.format(isbn.getIsbn10())); // output: 0-13-187248-6
            System.out.println(format.format(isbn.getIsbn13())); // output: 978-0-13-187248-6
        } catch (ISBNException e) {
            // Invalid isbn string
            e.printStackTrace(); // Reason
        }
    }
}
