package main;

import action.Command;
import action.Query.QueryActor;
import action.Query.QueryUser;
import action.Query.QueryVideo;
import action.Recommendation;
import checker.Checker;
import checker.Checkstyle;
import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import repository.ActorsRepo;
import repository.MoviesRepo;
import repository.SerialsRepo;
import repository.UsersRepo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implantation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        // initialise the repositories using the input data
        MoviesRepo moviesRepo = new MoviesRepo(input);
        SerialsRepo serialsRepo = new SerialsRepo(input);
        UsersRepo usersRepo = new UsersRepo(input);
        ActorsRepo actorsRepo = new ActorsRepo(input);

        // initialise the view and favorite count of all serials and movies
        moviesRepo.initialiseViewNumber(usersRepo);
        moviesRepo.initialiseFavoriteNumber(usersRepo);
        serialsRepo.initialiseViewNumber(usersRepo);
        serialsRepo.initialiseFavoriteNumber(usersRepo);

        // iterate through each input command and perform the given action
        for (ActionInputData inputAction : input.getCommands()) {
            switch (inputAction.getActionType()) {
                case Constants.COMMAND -> {
                    Command command = new Command();
                    switch (inputAction.getType()) {
                        case Constants.FAVORITE -> arrayResult.add(fileWriter.
                                writeFile(inputAction.getActionId(), null,
                                        command.addFavorite(inputAction, usersRepo,
                                                moviesRepo, serialsRepo)));
                        case Constants.VIEW -> arrayResult.add(fileWriter.
                                writeFile(inputAction.getActionId(), null,
                                        command.addView(inputAction, usersRepo,
                                                moviesRepo, serialsRepo)));
                        case Constants.RATING -> arrayResult.add(fileWriter.
                                writeFile(inputAction.getActionId(), null,
                                        command.addRating(inputAction, usersRepo,
                                                moviesRepo, serialsRepo)));
                        default -> System.out.println("Error parsing input");
                    }
                }
                case Constants.QUERY -> {
                    switch (inputAction.getObjectType()) {
                        case Constants.ACTORS -> {
                            QueryActor queryActor = new QueryActor();
                            arrayResult.add(fileWriter.writeFile(inputAction.getActionId(),
                                    null, queryActor.applyQuery(inputAction, actorsRepo,
                                            moviesRepo, serialsRepo, inputAction.getCriteria())));
                        }
                        case Constants.MOVIES, Constants.SHOWS -> {
                            QueryVideo queryVideo = new QueryVideo();
                            arrayResult.add(fileWriter.writeFile(inputAction.getActionId(),
                                    null, queryVideo.applyQuery(inputAction, moviesRepo,
                                            serialsRepo, inputAction.getCriteria())));
                        }
                        case Constants.USERS -> {
                            QueryUser queryUser = new QueryUser();
                            arrayResult.add(fileWriter.writeFile(inputAction.getActionId(),
                                    null, queryUser.applyQuery(inputAction, usersRepo)));
                        }
                        default -> System.out.println("Error parsing input");
                    }
                }
                case Constants.RECOMMENDATION -> {
                    Recommendation recommendation = new Recommendation();
                    arrayResult.add(fileWriter.writeFile(inputAction.getActionId(),
                            null, recommendation.applyRecommendation(inputAction, usersRepo,
                                    moviesRepo, serialsRepo, inputAction.getType())));
                }
                default -> System.out.println("Error parsing input");
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
