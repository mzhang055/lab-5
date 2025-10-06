package usecase;

import api.GradeDataBase;
import entity.Grade;
import entity.Team;

/**
 * GetAverageGradeUseCase class.
 */
public final class GetAverageGradeUseCase {
    private final GradeDataBase gradeDataBase;

    public GetAverageGradeUseCase(GradeDataBase gradeDataBase) {
        this.gradeDataBase = gradeDataBase;
    }

    /**
     * Get the average grade for a course across your team.
     * @param course The course.
     * @return The average grade.
     */
    public float getAverageGrade(String course) {
        // Call the API to get usernames of all your team members
        float sum = 0;
        int count = 0;
        final Team team = gradeDataBase.getMyTeam();

        // Get all team members
        final String[] members = team.getMembers();

        // For each team member, get their grades for the course
        for (String member : members) {
            final Grade[] grades = gradeDataBase.getGrades(member);

            // Find the grade for the specified course
            for (Grade grade : grades) {
                if (grade.getCourse().equals(course)) {
                    sum += grade.getGrade();
                    count++;
                    break; // Found the course, move to next member
                }
            }
        }

        if (count == 0) {
            return 0;
        }
        return sum / count;
    }
}
