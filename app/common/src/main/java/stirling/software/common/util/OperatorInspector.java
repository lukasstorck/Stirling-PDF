package stirling.software.common.util;

import java.io.IOException;
import java.util.*;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.pdmodel.PDDocument;

public class OperatorInspector extends PDFStreamEngine {

    private final List<Map<String, Object>> elements = new ArrayList<>();
    private final PDDocument document;

    public OperatorInspector(PDDocument document) {
        this.document = document;
    }

    public List<Map<String, Object>> getElements() {
        return elements;
    }

    @Override
    protected void processOperator(Operator operator, List<COSBase> operands) throws IOException {
        String opName = operator.getName();

        Map<String, Object> element = new HashMap<>();

        // --- TEXT ---
        if ("Tj".equals(opName) || "'".equals(opName) || "\"".equals(opName)) {
            COSString cosString = (COSString) operands.get(operands.size() - 1);
            element.put("operator", opName);
            element.put("text", cosString.getString());
        } else if ("TJ".equals(opName)) {
            COSArray array = (COSArray) operands.get(0);
            StringBuilder sb = new StringBuilder();
            for (COSBase obj : array) {
                if (obj instanceof COSString) {
                    sb.append(((COSString) obj).getString());
                }
            }
            element.put("operator", opName);
            element.put("text", sb.toString());
        }

        if (!element.isEmpty()) {
            elements.add(element);
        }

        super.processOperator(operator, operands);
    }
}
