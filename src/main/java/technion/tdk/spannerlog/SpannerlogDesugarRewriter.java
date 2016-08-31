package technion.tdk.spannerlog;

import technion.tdk.spannerlog.rgx.Rgx;

import java.util.List;
import java.util.stream.Collectors;

class SpannerlogDesugarRewriter {

    Program derive(Program program) {

        List<Regex> RegexList = program.getStatements()
                .stream()
                .filter(stmt -> stmt instanceof ConjunctiveQuery)
                .map(stmt -> (ConjunctiveQuery) stmt)
                .flatMap(cq -> cq.getBody().getBodyAtoms().stream())
                .filter(atom -> atom instanceof Regex)
                .map(atom -> (Regex) atom)
                .collect(Collectors.toList());

        RegexList.forEach(regex -> System.out.println(regex.getRegex()));
        Rgx rgx = new Rgx();
        RegexList.forEach(regex -> System.out.println(rgx.compileRegex(regex.getRegex())));


        return program;
    }
}
