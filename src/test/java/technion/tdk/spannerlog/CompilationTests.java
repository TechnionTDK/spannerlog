package technion.tdk.spannerlog;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CompilationTests {

//    @Test
//    public void compileBooleanCQ() {
//
//        try {
//            String splogSrc = "Q() :- R(True).";
//            SpannerlogInputParser parser = new SpannerlogInputParser();
//            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));
//
//            SpannerlogCompiler compiler = new SpannerlogCompiler();
//            compiler.compile(program);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void compileNonBooleanCQ() {
//        try {
//            String splogSrc = "Path(x,z) :- Edge(x,y),Path(y,z).";
//            SpannerlogInputParser parser = new SpannerlogInputParser();
//            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));
//
//            SpannerlogCompiler compiler = new SpannerlogCompiler();
//            compiler.compile(program);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void compileQueryWithLiterals() {
//        try {
//            String splogSrc = "Q() :- R(False, \"Hello\", 4, -2, 0.01, - 1.0,  [3,4]).";
//            SpannerlogInputParser parser = new SpannerlogInputParser();
//            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));
//
//            SpannerlogCompiler compiler = new SpannerlogCompiler();
//            compiler.compile(program);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Test
//    public void compileQueryWithSpans() {
//        try {
//            String splogSrc = "Q(\"Hello World\"[1,6][2,4], s[t]) :- R(s,t).";
//            SpannerlogInputParser parser = new SpannerlogInputParser();
//            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));
//
//            SpannerlogCompiler compiler = new SpannerlogCompiler();
//            compiler.compile(program);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void compileQueryWithIEFunctions() {
//        try {
//            String splogSrc = "Q() :- Doc(s), R<s[y]>(x).";
//            SpannerlogInputParser parser = new SpannerlogInputParser();
//            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));
//
//            SpannerlogCompiler compiler = new SpannerlogCompiler();
//            compiler.compile(program);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void compileAnbnQuery() {
        try {
            String splogSrc = "Q() :- doc(s), rgx1<s>(x,y), rgx2<s[y]>(x).";
            SpannerlogInputParser parser = new SpannerlogInputParser();
            Program program = parser.parseProgram(new ByteArrayInputStream(splogSrc.getBytes(StandardCharsets.UTF_8)));

            SpannerlogCompiler compiler = new SpannerlogCompiler();
            compiler.compile(program);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
