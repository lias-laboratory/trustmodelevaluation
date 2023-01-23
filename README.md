# Trust Model Evaluation (TME) Tool

Recommendation systems have taken the turn of the new uses of the Internet, with the emergence of trust-based recommendation systems. These use trust relationships between users to predict ratings based on experiences and feedback. To obtain these ratings, we need to use computational methods. Thus, a variety of computational models have been defined to help users make decisions, and to improve interactions between different users in a system. Because of the variety of models developed for this purpose, we have developed an approach to, firstly, help the user to choose the requirements of his/her system and, secondly, to help him/her to choose the most appropriate model for his application according to his/her requirements.

In the following document, we present the TME tool (Trust Model Evaluation) which automatically assess computational models against expressed requirements.

This work is carried out thanks to the support of the [ANRT](https://www.anrt.asso.fr/fr) through the CIFRE (Industrial Agreements for Training through Research) program. The project is supported by [OCode](https://www.ocode.team/) company and [LIAS laboratory (ISAE-ENSMA)](https://www.lias-lab.fr/).

**This tool is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.**

## Software requirements

* Java >= 11
* An integrated development environment (IDE). We will use Eclipse IDE: https://eclipse.org/downloads/, but any other IDE can be used
* Git
* All operating systems that support Java

## Source code

* Clone this project:

```console
$ git clone https://github.com/lias-laboratory/trustmodelevaluation
```

## Configure your IDE

* Open Eclipse IDE.

* Import Maven Project @File => Import => Maven => Existing Maven Projects@ and choose the directory of the cloned project (_trustmodelevaluation_).

## Motivating example

Let's take the example of a carpooling systems, for example a system like [Blablacar](https://www.blablacar.fr/). The principle of carpooling is to share a trip between a driver who has free places with passengers who are travelling to the same destination and who do not know each other. There are therefore two types of members in this type of application: the driver and the passenger. After completing a trip, members can leave notes. The principle of rating in a carpooling is to evaluate performance in the form of discrete verbal statements (Excellent, Good, Correct, Disappointing, Very Disappointing). A driver can rate the passenger to evaluate him/her and recommend him/her to the application's community. A passenger can also rate the driver to evaluate the experience of carpooling with him/her. A passenger cannot rate another passenger. 

This principle introduces the concept of trust which is one of the pillars of the carpooling community. This allows a member of the community to decide to carpool with someone he/she does not know.

From the reviews left for each profile, the members are able to accumulate a "trust capital", which results from the combination of the evaluations given during the sequence of interactions. This capital can increase if the rating is positive or decrease if it is negative.

Let's express these requirements in first time and evaluate the most appropriate computational model for his application according to our requirements.

## Create a sample

* Create a new Java class called `fr.ensma.lias.trustmodelevaluation.Sample` into the test directory _src/test/java_ and add it a Java method called `sample()`:

```java
public class Sample {

    @Test
    public void sample() {
        ... // Future code inside
    }
}
```

All the code will be inserted into this Java method.

### Create a model for application requirements

```java
// Rating type based on comment.
Comment comment = new Comment(0, 5);
// DriverRate (Comment > 2)
PositiveAction driverRatePositiveAction = new PositiveAction(new ApplicationRequirementConstraint(comment, new Greater(), new ApplicationRequirementValue(2)));
// DriverRate (Comment < 2)
NegativeAction driverRateNegativeAction = new NegativeAction(new ApplicationRequirementConstraint(comment, new Lower(), new ApplicationRequirementValue(2)));
```

### Create a model for trust requirements

```java
// Weighted toward current behavior.
// Requirement(score:[0..100]):(Weighted toward current behavior{null})^1>>[(Positive Task 1{score>+10%})^7;(Negative Task 2{score<-20%})^4]

Task wtcb = new AbstractTask("Weighted toward current behavior");
wtcb.setDecomposition(Decomposition.SEQ);
TrustRequirement wtcbRequirement = new TrustRequirement(wtcb, scoreElement);

Task positiveTask1Wtch = new PositiveTask("Positive Task 1");
positiveTask1Wtch.setIteration(7);
positiveTask1Wtch.addReason(driverRatePositiveAction);
TrustRequirementConstraint positiveTask1ConstraintWtch = new TrustRequirementConstraint(scoreElement,
		new Greater(), new PercentageScoreValue(10, 20));
positiveTask1Wtch.setConstraint(positiveTask1ConstraintWtch);

Task negativeTask2Wtch = new NegativeTask("Negative Task 2");
negativeTask2Wtch.addReason(driverRateNegativeAction);
negativeTask2Wtch.setIteration(4);
TrustRequirementConstraint negativeTask2ConstraintWtch = new TrustRequirementConstraint(scoreElement,
		new Lower(), new PercentageScoreValue(-35));
negativeTask2Wtch.setConstraint(negativeTask2ConstraintWtch);

wtcb.addTask(positiveTask1Wtch);
wtcb.addTask(negativeTask2Wtch);

TrustRequirementEngine engine = new TrustRequirementEngine();
Scenario wtcbScenario = engine.eval(wtcbRequirement);

Assert.assertEquals(11, wtcbScenario.getLength());

for (SimulatedTask current : wtcbScenario.getSimulatedTasks()) {
    System.out.println(current);
}
```

### Assess computational models

```java
ComputationalModelRecommandationEngine ceEngine = new ComputationalModelRecommandationEngine();
ReportEvaluation reportEvaluation = ceEngine.eval(wtcbScenario, new BayesienNetworkComputationalModel(), new ArithmeticFunction());
System.out.println(reportEvaluation.toString());
reportEvaluation = ceEngine.eval(wtcbScenario, new EigenTrustNetworkComputationalModel(), new ArithmeticFunction());
System.out.println(reportEvaluation.toString());
```

### Display results

* Execute a JUnit Run configuration on `fr.ensma.lias.trustmodelevaluation.Sample` class:

```console
Positive Task 1 score > 22
Positive Task 1 score > 24
Positive Task 1 score > 26
Positive Task 1 score > 28
Positive Task 1 score > 30
Positive Task 1 score > 33
Positive Task 1 score > 36
Negative Task 2 score < 23
Negative Task 2 score < 14
Negative Task 2 score < 9
Negative Task 2 score < 5
BayesienNetworkComputationalModel: 42
EigenTrustNetworkComputationalModel: 18
```

## Software licence agreement

Details the license agreement of TME: [LICENCE](LICENCE)

## Historic Contributors (core developers first followed by alphabetical order)

* [Chayma SELLAMI (core developer](https://www.lias-lab.fr/fr/members/chaymasellami/) (O°Code and LIAS/ISAE-ENSMA)
* [Mickael BARON](https://www.lias-lab.fr/fr/members/mickaelbaron/) (LIAS/ISAE-ENSMA)
* Mournir BECHCHI (O°Code)
* Dominique CHABOT (O°Code)
* [Allel HADJALI](https://www.lias-lab.fr/fr/members/allelhadjali/) (LIAS/ISAE-ENSMA)
* [Stéphane JEAN](https://www.lias-lab.fr/fr/members/stephanejean/) (LIAS/ISAE-ENSMA)

## Code Analysis

* Lines of Code: 2200
* Programming Language: Java
