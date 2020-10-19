package utilities;

import android.graphics.PointF;

public class SQLUtility {

    public static String getBetweenTwoAttributesPredicate(String lowerOrEqualAttribute, String value, String greaterOrEqualAttribute, boolean isText) {
        String statement = "";
        if (lowerOrEqualAttribute != null && greaterOrEqualAttribute != null && value != null) {
            if (lowerOrEqualAttribute.length() > 0 && greaterOrEqualAttribute.length() > 0 && value.length() > 0) {
                if (!isText) {
                    statement = "(" + lowerOrEqualAttribute + " <= " + value + " and " + value + " <= " + greaterOrEqualAttribute + ")";
                } else {
                    statement = "(" + lowerOrEqualAttribute + " <= '" + value + "' and '" + value + "' <= " + greaterOrEqualAttribute + ")";
                }
            }
        }
        return statement;
    }

    public static String getGreaterOrEqualPredicate(String attribute, String value, boolean isText) {
        String statement = "";
        if (attribute != null && value != null) {
            if (attribute.length() > 0 && value.length() > 0) {
                if (!isText) {
                    statement = "(" + value + " >= " + attribute + ")";
                } else {
                    statement = "('" + value + "' >= " + attribute + ")";
                }
            }
        }
        return statement;
    }

    public static String getLowerOrEqualPredicate(String attribute, String value, boolean isText) {
        String statement = "";
        if (attribute != null && value != null) {
            if (attribute.length() > 0 && value.length() > 0) {
                if (!isText) {
                    statement = "(" + value + " <= " + attribute + ")";
                } else {
                    statement = "('" + value + "' <= " + attribute + ")";
                }
            }
        }
        return statement;
    }

    public static String getEqualPredicate(String attribute, String value, boolean isText) {
        String statement = "";
        if (attribute != null && value != null) {
            if (attribute.length() > 0 && value.length() > 0) {
                statement = "(";
                if (!isText) {
                    statement = statement + attribute + " = " + value + ")";
                }
                else {
                    statement = statement + attribute + " = '" + value + "')";
                }
            }
        }
        return statement;
    }

    public static String getOrEqualPredicate(String attribute, boolean isText, String...values) {
        String statement = "";
        if (attribute != null && values != null) {
            if (attribute.length() > 0) {
                int i;
                statement = "(";
                if (!isText) {
                    for (i = 0; i < values.length; i++) {
                        if (values[i] != null && values[i].length() > 0) {
                            statement = statement + attribute + " = " + values[i] + " or ";
                        }
                    }
                } else {
                    for (i = 0; i < values.length; i++) {
                        if (values[i] != null && values[i].length() > 0) {
                            statement = statement + attribute + " = " + "'" + values[i] + "'" + " or ";
                        }
                    }
                }
                statement = statement + ")";
                if (statement.length() == 2) {
                    return "";
                }
                statement = statement.replaceAll("( or \\))$", ")");
            }
        }
        return statement;
    }

    public static String getAndLikePredicate(String attribute, String...values) {
        String statement = "";
        if (attribute != null && values != null) {
            if (attribute.length() > 0) {
                int i;
                statement = "(";
                for (i = 0; i < values.length; i++) {
                    if (values[i] != null) {
                        if (values[i].length() > 0) {
                            statement = statement + attribute + " like " + "'%" + values[i] + "%'" + " and ";
                        }
                    }
                }
                statement = statement + ")";
                if (statement.length() == 2) {
                    return "";
                }
                statement = statement.replaceAll("( and \\))$", ")");
            }
        }
        return statement;
    }

    public static String getNotEqualPredicate(String attribute, String value, boolean isText) {
        String statement = "";
        if (attribute != null && value != null) {
            if (attribute.length() > 0 && value.length() > 0) {
                statement = "(";
                if (!isText) {
                    statement = statement + attribute + " <> " + value + ")";
                }
                else {
                    statement = statement + attribute + " <> '" + value + "')";
                }
            }
        }
        return statement;
    }

    public static String getAndProposition(String...predicates) {
        String proposition = "";
        if (predicates != null) {
            proposition = "(";
            int i;
            for (i = 0; i < predicates.length; i++) {
                if (predicates[i] != null) {
                    if (predicates[i].length() > 0) {
                        proposition = proposition + predicates[i] + " and ";
                    }
                }
            }
            proposition = proposition + ")";
            if (proposition.length() == 2) {
                return "";
            }
            proposition = proposition.replaceAll("( and \\))$", ")");
        }
        return proposition;
    }

    public static String getOrProposition(String...predicates) {
        String proposition = "";
        if (predicates != null) {
            proposition = "(";
            int i;
            for (i = 0; i < predicates.length; i++) {
                if (predicates[i] != null) {
                    if (predicates[i].length() > 0) {
                        proposition = proposition + predicates[i] + " or ";
                    }
                }
            }
            proposition = proposition + ")";
            if (proposition.length() == 2) {
                return "";
            }
            proposition = proposition.replaceAll("( or \\))$", ")");
        }
        return proposition;
    }

    public static String getRadiusFromCenterPredicate(PointF positionPointF, String latitudeAttribute, String longitudeAttribute, String radiusFromCenterValue) {
        String statement = "";
        if (positionPointF != null && latitudeAttribute != null && longitudeAttribute != null && radiusFromCenterValue != null) {
            if (latitudeAttribute.length() > 0 && longitudeAttribute.length() > 0 && radiusFromCenterValue.length() > 0) {
                double radius = Double.valueOf(radiusFromCenterValue);
                PointF pointOne = PositionUtility.getDerivedPosition(positionPointF, radius, PositionUtility.DEGREE_ONE);
                if (pointOne == null) {
                    return statement;
                }
                PointF pointTwo = PositionUtility.getDerivedPosition(positionPointF, radius, PositionUtility.DEGREE_TWO);
                PointF pointThree = PositionUtility.getDerivedPosition(positionPointF, radius, PositionUtility.DEGREE_THREE);
                PointF pointFour = PositionUtility.getDerivedPosition(positionPointF, radius, PositionUtility.DEGREE_FOUR);
                statement = ("(" + latitudeAttribute + " > " + pointThree.x + " and " + latitudeAttribute + " < " + pointOne.x + " and " + longitudeAttribute + " < " + pointTwo.y + " and " + longitudeAttribute + " > " + pointFour.y + ")");
            }
        }
        return statement;
    }

}