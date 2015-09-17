package nl.uva.se.funcpipes;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Program {
	
	public static void main(String[] args) {
		//examples();
		split();
	}
	
	public static void examples() {
		Pipeline<Integer> p = new Pipeline<Integer>();
		
		
		System.out.println("Push a number through a print function");
		p.via(i -> System.out.println("Pushed " + i));
		p.feed(5);
		p.clear();
		
		
		System.out.println("Push 5 numbers through a print function");
		p.via(i -> System.out.println("Pushed " + i));
		p.feed(Arrays.asList(1, 2, 3, 4, 5));
		p.clear();
		

		System.out.println("Multiply all even numbers up to 25 and print");
		p	.filter(i -> i % 2 == 0)
			.via(i -> System.out.println("Even " + i));
		
		p.feed(intRange(0, 25));
		p.clear();
		
		
		System.out.println("Duplicate input");
		p	.expand(i -> Arrays.asList(i, i))
			.via(print());
		
		p.feed(Arrays.asList(1, 2, 3));
		

		System.out.println("Integer to Float");
		p	.via(print())
			.transform(i -> new Float(i))
			.via(print());
				
		p.feed(1);
		p.clear();
		
		
		System.out.println("FizzBuzz, print 1..100, mul 3 - fizz, mul 5 - buzz, mul3&5 - fizzbuzz");
		p	.filter(i -> { boolean fb = i % 3 == 0 && i % 5 == 0; if (fb) System.out.println("FizzBuzz"); return !fb; })
			.filter(i -> { boolean fb = i % 5 == 0; if (fb) System.out.println("Buzz"); return !fb; })
			.filter(i -> { boolean fb = i % 3 == 0; if (fb) System.out.println("Fizz"); return !fb; })
			.via(i -> System.out.println(i));
		
		p.feed(intRange(1, 100));
		p.clear();
	}
	
	public static void split() {
		
		Pipeline<Integer> p = new Pipeline<Integer>();
		
		Buffer<Integer, Integer, Integer> b = new Buffer<Integer, Integer, Integer>();
		PipelineBuilder<Integer> builder = b;
		b.fillFirstArg(5);
		b.fillSecondArg(6);
		b.setFunc((x, y) -> { print().accept("Combining !"); return x+y;});
		b.flush();
		
		builder.via(print());
		builder.via(print());
		b.fillFirstArg(5);
		b.fillSecondArg(6);
		b.flush();
		return;
				
//		p	.via(i -> i * 2)
//			.split()
//				.to(newPipe -> {
//					newPipe.via(i -> System.out.println("Hi " + i));
//				})
//				.to(newPipe -> {
//					newPipe.via(i -> System.out.println("Ho " + i));
//				})
//				.to(newPipe -> {
//					newPipe.via(i -> System.out.println("Ha  " + i));
//				});
//		
//		p.feed(5);
//		
//		
//		// With buffers
//		final Buffer<Integer, Integer, Integer> b2 = new Buffer<Integer, Integer, Integer>(
//				(a, b) -> {
//					Integer result = a + b;
//					System.out.println("Buffer 2: " + a + " " + b + " -> " + result);
//					return result;
//				});
//		
//		final Buffer<Integer, Integer, Integer> b1 = new Buffer<Integer, Integer, Integer>(
//				(a, b) -> {
//					Integer result = a * b;
//					System.out.println("Buffer 1: " + a + " " + b + " -> " + result);
//					b2.fillFirstArg(result);
//					return result;
//				});
//		
//		b2.via(result -> System.out.println("Yaya! " + result));
//		
//		
//		p	.via(i -> i * 2)
//			.split()
//				.to(newPipe -> {
//					newPipe	.via(i -> System.out.println("Hi " + i))
//							.via(i -> b1.fillFirstArg(i));
//				})
//				.to(newPipe -> {
//					newPipe	.via(i -> System.out.println("Ho " + i))
//							.via(i -> b1.fillSecondArg(i))
//							.via(i -> b1.flush());
//				})
//				.to(newPipe -> {
//					newPipe	.via(i -> System.out.println("Ha  " + i))
//							.via(i -> b2.fillSecondArg(i))
//							.via(i -> b2.flush());
//				});
//	
//		p.feed(5);
	}
	
	
	private static List<Integer> intRange(int incLower, int incUpper) {
		return IntStream.rangeClosed(incLower, incUpper).boxed().collect(Collectors.toList());
	}
	
	public static <T> Consumer<T> print() {
		return t -> System.out.println(t);
	}
}