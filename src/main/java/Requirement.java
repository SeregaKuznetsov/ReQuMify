public class Requirement {

    private int id;
    private String description;
    private Integer index_end;
    private Integer index_start;
    private String language_construct;
    private int ambigueTerms = 0;
    private int connectiveTerms = 0;
    private int subjectivityTerms = 0;
    private String subjective_language;
    private String text;
    private String ReqText;
    private String title;
    private double unambiguity;
    private double unsubjectivity;
    private double singularity;

    public String getReqText() {
        return ReqText;
    }

    public void setReqText(String reqText) {
        ReqText = reqText;
    }

    public double getUnambiguity() {
        return unambiguity;
    }

    public void setUnambiguity(double unambiguity) {
        this.unambiguity = unambiguity;
    }

    public double getUnsubjectivity() {
        return unsubjectivity;
    }

    public void setUnsubjectivity(double unsubjectivity) {
        this.unsubjectivity = unsubjectivity;
    }

    public double getSingularity() {
        return singularity;
    }

    public void setSingularity(double singularity) {
        this.singularity = singularity;
    }

    public Requirement(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getAmbigueTerms() {
        return ambigueTerms;
    }

    public void setAmbigueTerms(int ambigueTerms) {
        this.ambigueTerms = ambigueTerms;
    }

    public int getConnectiveTerms() {
        return connectiveTerms;
    }

    public void setConnectiveTerms(int connectiveTerms) {
        this.connectiveTerms = connectiveTerms;
    }

    public int getSubjectivityTerms() {
        return subjectivityTerms;
    }

    public void setSubjectivityTerms(int subjectivityTerms) {
        this.subjectivityTerms = subjectivityTerms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIndex_end() {
        return index_end;
    }

    public void setIndex_end(Integer index_end) {
        this.index_end = index_end;
    }

    public Integer getIndex_start() {
        return index_start;
    }

    public void setIndex_start(Integer index_start) {
        this.index_start = index_start;
    }

    public String getLanguage_construct() {
        return language_construct;
    }

    public void setLanguage_construct(String language_construct) {
        this.language_construct = language_construct;
    }

    public String getSubjective_language() {
        return subjective_language;
    }

    public void setSubjective_language(String subjective_language) {
        this.subjective_language = subjective_language;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
