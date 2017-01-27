# Rate Limiter

There is a limit of `N` tokens available for use per second. Write a component that will answer the question - `can I pass?`. If yes - the token is consumed.

The tokens are used as permits by clients of the system. If token is available the client will do some work, otherwise it’ll need to give up during current second. 

Clients run in separate threads.
