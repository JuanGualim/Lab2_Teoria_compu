import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/** Visualización sencilla del AFN construida únicamente con Java Swing. */
public class VisualizadorAFN {

    public static void mostrar(AFN afn, String expresion) {
        if (afn == null || afn.getEstados().isEmpty()) {
            System.out.println("No hay un AFN para visualizar.");
            return;
        }
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Vista gráfica del AFN no disponible en este entorno.");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("AFN de Thompson - " + expresion);
            ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ventana.add(new JScrollPane(new PanelAFN(afn)));
            ventana.setSize(1200, 650);
            ventana.setLocationByPlatform(true);
            ventana.setVisible(true);
        });
    }

    private static class PanelAFN extends JPanel {
        private static final int RADIO = 25;
        private static final int ESPACIO = 115;
        private static final int MARGEN = 85;
        private static final int Y_ESTADOS = 315;

        private final AFN afn;
        private final List<Estado> estados;
        private final Map<Estado, Integer> posicionesX = new LinkedHashMap<>();

        PanelAFN(AFN afn) {
            this.afn = afn;
            estados = new ArrayList<>(afn.getEstados());
            estados.sort(Comparator.comparingInt(Estado::getId));

            for (int i = 0; i < estados.size(); i++) {
                posicionesX.put(estados.get(i), MARGEN + i * ESPACIO);
            }

            int ancho = Math.max(1100,
                    MARGEN * 2 + Math.max(1, estados.size() - 1) * ESPACIO);
            setPreferredSize(new Dimension(ancho, 600));
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.setStroke(new BasicStroke(1.5f));

            dibujarTitulo(g);
            dibujarTransiciones(g);
            dibujarEstados(g);
            dibujarIndicadorInicial(g);
            g.dispose();
        }

        private void dibujarTitulo(Graphics2D g) {
            g.setColor(Color.DARK_GRAY);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 15f));
            g.drawString("Inicio: " + afn.getEstadoInicial()
                    + "     Aceptación: " + afn.getEstadoAceptacion(), 25, 30);
            g.setFont(g.getFont().deriveFont(Font.PLAIN, 12f));
        }

        private void dibujarTransiciones(Graphics2D g) {
            // Las etiquetas con el mismo origen y destino se agrupan para que
            // dos transiciones no queden dibujadas exactamente una sobre otra.
            Map<ParEstados, List<String>> agrupadas = new LinkedHashMap<>();
            for (Transicion transicion : afn.getTransiciones()) {
                ParEstados par = new ParEstados(transicion.getOrigen(),
                        transicion.getDestino());
                List<String> etiquetas = agrupadas.computeIfAbsent(par,
                        clave -> new ArrayList<>());
                if (!etiquetas.contains(transicion.getSimbolo())) {
                    etiquetas.add(transicion.getSimbolo());
                }
            }

            g.setColor(new Color(70, 70, 70));
            for (Map.Entry<ParEstados, List<String>> entrada
                    : agrupadas.entrySet()) {
                ParEstados par = entrada.getKey();
                String etiqueta = String.join(", ", entrada.getValue());
                dibujarFlechaCurva(g, par.origen(), par.destino(), etiqueta);
            }
        }

        private void dibujarFlechaCurva(Graphics2D g, Estado origen,
                                        Estado destino, String etiqueta) {
            int x1 = posicionesX.get(origen);
            int x2 = posicionesX.get(destino);

            if (origen == destino) {
                int diametro = RADIO * 2;
                g.drawArc(x1 - RADIO, Y_ESTADOS - RADIO - 38,
                        diametro, 48, 15, 300);
                dibujarCabeza(g, x1 + RADIO - 4, Y_ESTADOS - RADIO - 5,
                        0.8, 0.6);
                dibujarEtiqueta(g, etiqueta, x1, Y_ESTADOS - RADIO - 45);
                return;
            }

            int distancia = Math.abs(destino.getId() - origen.getId());
            int altura = 28 + Math.min(155, distancia * 15);
            int signo = x2 > x1 ? -1 : 1;
            double controlX = (x1 + x2) / 2.0;
            double controlY = Y_ESTADOS + signo * altura;

            double direccionInicioX = controlX - x1;
            double direccionInicioY = controlY - Y_ESTADOS;
            double largoInicio = Math.hypot(direccionInicioX,
                    direccionInicioY);
            double inicioX = x1 + RADIO * direccionInicioX / largoInicio;
            double inicioY = Y_ESTADOS + RADIO * direccionInicioY / largoInicio;

            double direccionFinX = x2 - controlX;
            double direccionFinY = Y_ESTADOS - controlY;
            double largoFin = Math.hypot(direccionFinX, direccionFinY);
            double finX = x2 - RADIO * direccionFinX / largoFin;
            double finY = Y_ESTADOS - RADIO * direccionFinY / largoFin;

            QuadCurve2D curva = new QuadCurve2D.Double(inicioX, inicioY,
                    controlX, controlY, finX, finY);
            g.draw(curva);
            dibujarCabeza(g, finX, finY, direccionFinX, direccionFinY);

            int etiquetaX = (int) ((inicioX + 2 * controlX + finX) / 4);
            int etiquetaY = (int) ((inicioY + 2 * controlY + finY) / 4)
                    + (signo < 0 ? -7 : 16);
            dibujarEtiqueta(g, etiqueta, etiquetaX, etiquetaY);
        }

        private void dibujarEstados(Graphics2D g) {
            FontMetrics medidas = g.getFontMetrics();
            for (Estado estado : estados) {
                int x = posicionesX.get(estado);
                g.setColor(new Color(225, 239, 255));
                g.fillOval(x - RADIO, Y_ESTADOS - RADIO,
                        RADIO * 2, RADIO * 2);
                g.setColor(Color.BLACK);
                g.drawOval(x - RADIO, Y_ESTADOS - RADIO,
                        RADIO * 2, RADIO * 2);

                if (estado == afn.getEstadoAceptacion()) {
                    g.drawOval(x - RADIO + 5, Y_ESTADOS - RADIO + 5,
                            (RADIO - 5) * 2, (RADIO - 5) * 2);
                }

                String nombre = estado.toString();
                int textoX = x - medidas.stringWidth(nombre) / 2;
                int textoY = Y_ESTADOS + medidas.getAscent() / 2 - 2;
                g.drawString(nombre, textoX, textoY);
            }
        }

        private void dibujarIndicadorInicial(Graphics2D g) {
            int x = posicionesX.get(afn.getEstadoInicial());
            int inicio = x - RADIO - 45;
            int fin = x - RADIO;
            g.drawLine(inicio, Y_ESTADOS, fin, Y_ESTADOS);
            dibujarCabeza(g, fin, Y_ESTADOS, 1, 0);
            g.drawString("inicio", inicio - 8, Y_ESTADOS - 9);
        }

        private static void dibujarCabeza(Graphics2D g, double x, double y,
                                           double dx, double dy) {
            double angulo = Math.atan2(dy, dx);
            double largo = 10;
            double apertura = Math.PI / 7;
            Path2D cabeza = new Path2D.Double();
            cabeza.moveTo(x, y);
            cabeza.lineTo(x - largo * Math.cos(angulo - apertura),
                    y - largo * Math.sin(angulo - apertura));
            cabeza.lineTo(x - largo * Math.cos(angulo + apertura),
                    y - largo * Math.sin(angulo + apertura));
            cabeza.closePath();
            g.fill(cabeza);
        }

        private static void dibujarEtiqueta(Graphics2D g, String texto,
                                             int centroX, int baseY) {
            FontMetrics medidas = g.getFontMetrics();
            int ancho = medidas.stringWidth(texto);
            g.setColor(new Color(255, 255, 255, 220));
            g.fillRect(centroX - ancho / 2 - 2,
                    baseY - medidas.getAscent(), ancho + 4,
                    medidas.getHeight());
            g.setColor(new Color(35, 35, 35));
            g.drawString(texto, centroX - ancho / 2, baseY);
        }

        private record ParEstados(Estado origen, Estado destino) {
        }
    }
}
