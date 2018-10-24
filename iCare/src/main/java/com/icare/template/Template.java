package main.java.com.icare.template;

import java.util.ArrayList;

public class Template {
  ArrayList<ArrayList<String>> template = new ArrayList<>();

  public Template() {}

  public void addColumn(String columnName) {
    if (template.size() == 0) {
      ArrayList<String> firstRow = new ArrayList<>();
      firstRow.add(0, columnName);
      template.add(0, firstRow);
    } else {
      ArrayList<String> firstLine = template.get(0);
      firstLine.add(firstLine.size(), columnName);
      int i = 1;
      while (i < template.size()) {
        ArrayList<String> line = template.get(i);
        line.add(line.size(), "");
        i += 1;
      }
    }
  }

  public void addRow(String name) {
    ArrayList<String> newLine = new ArrayList<>();
    newLine.add(0, name);
    template.add(template.size(), newLine);
    int i = 1;
    int length = template.get(0).size();
    while (i < length) {
      newLine.add(i, "");
      i += 1;
    }
  }

  public void fillUp(int columnNumber, int rowNumber, String info) {
    template.get(rowNumber).set(columnNumber, info);
  }

  public void moveColumn(int fromColumn, int toColumn) {
    int i = 0;
    while (i < template.size()) {
      ArrayList<String> list = template.get(i);
      String info = list.get(fromColumn);
      list.add(toColumn, info);
      if (fromColumn <= toColumn) {
        list.remove(fromColumn);
      } else {
        list.remove(fromColumn + 1);
      }
      i += 1;
    }
  }

  public void moveRow(int fromRow, int toRow) {
    template.add(toRow, template.get(fromRow));
    if (fromRow <= toRow) {
      template.remove(fromRow);
    } else {
      template.remove(fromRow + 1);
    }
  }

  public int getRowNumber() {
    return template.size();
  }

  public int getColumnNumber() {
    return template.get(0).size();
  }

  public void sortedByColumn(int columnNumber, String way) {
    int i = 1;
    ArrayList<ArrayList<String>> list = new ArrayList<>();
    if (template.size() > 1) {
      while (i < template.size()) {
        String item = template.get(i).get(columnNumber);
        if (list.size() == 0) {
          list.add(template.get(i));
        } else {
          int j = 0;
          boolean stop = false;
          while (j < list.size() && stop == false) {
            if (Double.valueOf(item) <= Double.valueOf(list.get(j).get(columnNumber))) {
              list.add(j, template.get(i));
              stop = true;
            }
            j += 1;
          }
          if (stop == false) {
            list.add(template.get(i));
          }
        }
        i += 1;
      }
      if (way == "decreasing") {
        i = 1;
        while (i < template.size()) {
          template.remove(i);
          template.add(i, list.get(list.size() - 1));
          list.remove(list.size() - 1);
          i += 1;
        }
      } else {
        i = 1;
        while (i < template.size()) {
          template.remove(i);
          template.add(i, list.get(0));
          list.remove(0);
          i += 1;
        }
      }
    }
  }

}
