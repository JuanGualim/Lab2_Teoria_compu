import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/** Dibuja un árbol sintáctico utilizando solamente Java Swing. */
public class VisualizadorArbol {
    private static final int ESPACIO_HOJA = 90;
    private static final int ESPACIO_NIVEL = 85;

    /** Abre una ventana para el árbol o informa si no hay entorno gráfico. */
    public static void mostrar(Nodo raiz, String expresion) {
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Vista gráfica no disponible en este entorno.");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Árbol Sintáctico - " + expresion);
            ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            PanelArbol panel = new PanelArbol(raiz);
            ventana.add(new JScrollPane(panel));
            ventana.setSize(900, 650);
            ventana.setLocationByPlatform(true);
            ventana.setVisible(true);
        });
    }

    /** Panel responsable de calcular posiciones y dibujar nodos y enlaces. */
    private static class PanelArbol extends JPanel {
        private static final int RADIO = 22;
        private final Nodo raiz;
        private final int anchoArbol;

        PanelArbol(Nodo raiz) {
            this.raiz = raiz;
            anchoArbol = Math.max(700, contarHojas(raiz) * ESPACIO_HOJA);
            int alto = Math.max(500, altura(raiz) * ESPACIO_NIVEL + 60);
            setPreferredSize(new Dimension(anchoArbol, alto));
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            dibujar(g, raiz, 25, anchoArbol - 25, 45);
        }

        private void dibujar(Graphics2D g, Nodo nodo, int minimoX,
                             int maximoX, int y) {
            if (nodo == null) {
                return;
            }

            int x = (minimoX + maximoX) / 2;
            int siguienteY = y + ESPACIO_NIVEL;
            Nodo izquierdo = nodo.getIzquierdo();
            Nodo derecho = nodo.getDerecho();

            if (izquierdo != null && derecho != null) {
                // Cada subárbol recibe espacio según su número de hojas.
                // Esto evita amontonar árboles desbalanceados.
                int hojasIzquierdas = contarHojas(izquierdo);
                int hojasDerechas = contarHojas(derecho);
                int division = minimoX + (maximoX - minimoX)
                        * hojasIzquierdas
                        / (hojasIzquierdas + hojasDerechas);
                int xIzquierdo = (minimoX + division) / 2;
                int xDerecho = (division + maximoX) / 2;
                g.setColor(Color.DARK_GRAY);
                g.drawLine(x, y, xIzquierdo, siguienteY);
                g.drawLine(x, y, xDerecho, siguienteY);
                dibujar(g, izquierdo, minimoX, division, siguienteY);
                dibujar(g, derecho, division, maximoX, siguienteY);
            } else if (izquierdo != null) {
                g.setColor(Color.DARK_GRAY);
                g.drawLine(x, y, x, siguienteY);
                dibujar(g, izquierdo, minimoX, maximoX, siguienteY);
            }

            g.setColor(new Color(220, 235, 252));
            g.fillOval(x - RADIO, y - RADIO, RADIO * 2, RADIO * 2);
            g.setColor(Color.BLACK);
            g.drawOval(x - RADIO, y - RADIO, RADIO * 2, RADIO * 2);

            FontMetrics medidas = g.getFontMetrics();
            int textoX = x - medidas.stringWidth(nodo.getValor()) / 2;
            int textoY = y + medidas.getAscent() / 2 - 2;
            g.drawString(nodo.getValor(), textoX, textoY);
        }

        private static int contarHojas(Nodo nodo) {
            if (nodo == null) {
                return 0;
            }
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                return 1;
            }
            return contarHojas(nodo.getIzquierdo())
                    + contarHojas(nodo.getDerecho());
        }

        private static int altura(Nodo nodo) {
            if (nodo == null) {
                return 0;
            }
            return 1 + Math.max(altura(nodo.getIzquierdo()),
                    altura(nodo.getDerecho()));
        }
    }
}
