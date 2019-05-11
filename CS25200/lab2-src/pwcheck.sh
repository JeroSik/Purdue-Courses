#!/bin/bash

#DO NOT REMOVE THE FOLLOWING TWO LINES
git add $0 >> .local.git.out
git commit -a -m "Lab 2 commit" >> .local.git.out
git push >> .local.git.out || echo

# Declare PASSWORD as user input
PASSWORD=$(cat < $1)
# Declare SCORE of password strength
SCORE=0
# Declare SIZE for length of PASSWORD
SIZE=${#PASSWORD}

# Check if password is invalid
if [ \( $SIZE -lt 6 \) -o \( $SIZE -gt 32 \) ]; then
  # Print error message
  echo "Error: Password length invalid."
else
  # Give 1 point for each character in the string
  let SCORE=SIZE

  # Give 5 points if one of the special characters exists
  if /bin/egrep -q '[#$\+%@]' $1 ; then
    let SCORE=SCORE+5
  fi
  # Give 5 points if at least one number exists
  if /bin/egrep -q '[0-9]+' $1 ; then
    let SCORE=SCORE+5
  fi
  # Give 5 points if at least one alphanumeric character exists
  if /bin/egrep -q '[A-Za-z]+' $1 ; then
    let SCORE=SCORE+5
  fi
  # Take 10 points if a repeated alphanumeric character exists
  if /bin/egrep -q '([A-Za-z0-9])\1+' $1 ; then
    let SCORE=SCORE-10
  fi
  # Take 3 points if 3 or more consecutive lowercase characters exist
  if /bin/egrep -q '[a-z][a-z][a-z]' $1 ; then
    let SCORE=SCORE-3
  fi
  # Take 3 points if 3 or more consecutive uppercase characters exist
  if /bin/egrep -q '[A-Z][A-Z][A-Z]' $1 ; then
    let SCORE=SCORE-3
  fi
  # Take 3 points if 3 or more consecutive numbers exist
  if /bin/egrep -q '[0-9][0-9][0-9]' $1 ; then
    let SCORE=SCORE-3
  fi

  # Print final score
  echo Password Score: $SCORE
fi
