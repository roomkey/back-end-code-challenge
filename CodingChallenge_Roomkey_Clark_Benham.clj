(ns room-key-coding-challenge.core)

(def n 10001); the number prime which will be found

(defn is-not-prime [number & [factors]] 
           (boolean (some integer? (map #(/ number %) factors))));;tests if a number is not a prime, based on a vector of all potential prime factors

(defn upper-bound [k] (Math/ceil (* k (+ (Math/log k)  (Math/log (Math/log k)) -1 (* 1.8  (Math/log (Math/log k)) (/  (Math/log k) ))))))	;;k*(ln(k)+ln(ln(k)-1+1.8*ln(ln(k))/ln(k))) formula for the maximium value of the Kth prime for k>13
(def upper-value (int (upper-bound n)));;finds the maximium possible integer value of the nth prime

(def biggest-factor (Math/ceil (Math/sqrt upper-value)));;to test if the  prime, see if division with a diffrent number will produce an integer, sqrt(number) is the biggest possible factor; sqrt(n)*sqrt(n)=n
(def num-factors (Math/ceil (/ (- biggest-factor 5) 2)));;subtracting values as the first 5 primes are given; testing increments by 2 so needs half as many steps

  
(def factors (reduce (fn [primes number] 
     (if (is-not-prime number primes)
   		primes
   		(conj primes number)));;if a number is a primes it is appended to the vector of primes; this is where vector of potential factors is created
   [2 3 5 7 11](take num-factors (iterate (partial + 2) 13))));;starts at 13 as the lower bound estimation for prime numbers is true for n>13; incrementing by 2 as no even number will be prime


;;function returns the nth prime from a starting odd number, given the vector of all possible prime factors
(defn nth-prime [start-prime prime-factors count] 
  (loop [search start-prime
         prime-factors prime-factors
         count count]
    (if (is-not-prime search prime-factors)
      (recur (inc (inc search)) prime-factors count);;if is not prime increase the number being test by 2(even number won't be prime) and rerun
      (if (> count 1) 
        (recur (inc (inc search)) prime-factors (dec count)) 
        search))));;if is prime decrease how many more primes need to be found 

(nth-prime (last factors) factors (- n (count factors)));;finds the prime number 10001- current prime# "spots" away from the given input




;;code below would be used for finding extremely large primes, as it takes a mathematical bound on the range
;(defn lower-bound [k] (Math/floor (* k (+ (Math/log k) (Math/log (Math/log k)) -1))));;minmum value the nth prime could be n*(ln(n)+ln(ln(n))-1)
;(def lower-value (int (lower-bound n)))
;(defn next-prime [start-prime prime-factors] 
;  (loop [search start-prime
;         prime-factors prime-factors]
;    (if (is-not-prime search prime-factors)
;      (recur (inc search) prime-factors)
;      search      )))
;;(next-prime lower-value factors)