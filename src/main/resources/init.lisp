
;;; Script for initializing the ABCL interpreter

(defun init-interpreter (swank-path)
  "Sets up the lisp interpreter and loads required libraries. Starts SWANK server on
port 4005. The 'swank-path' parameter should be a string with absolute path to .jar file
containing swank."
  (setf CL:*LOAD-VERBOSE* t)
  (require :abcl-contrib)
  (let ((swank-full-path (concatenate 'string
                                      "jar:file:"
                                      swank-path
                                      "!/swank/")))
    (push (pathname swank-full-path) asdf:*central-registry*))
  (asdf:load-system :swank)
  (swank:create-server :style :spawn :dont-close t))


(defun hello ()
  (format t "Hello!"))
;;(setf CL:*LOAD-VERBOSE* t)
                                        ;verbose loading for asdf systems
