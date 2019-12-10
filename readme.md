# PayMaya SDK for Android

[![](https://jitpack.io/v/CoreProc/paymaya-sdk-android.svg)](https://jitpack.io/#CoreProc/paymaya-sdk-android)

This is an unofficial SDK for PayMaya using Java but offers improvements over the official PayMaya codebase. 

## Installation

You can install the package via gradle:

1. Add this to your *root build.gradle* file
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add this to your *app-level build.gradle* file
```gradle
dependencies {
    implementation 'com.github.CoreProc:paymaya-sdk-android:{latest_version}'
}
```

## Usage

Read the documentation here:
https://developers.paymaya.com/blog/entry/paymaya-api-and-sdk-documentation

To get development API keys and credit cards, check the link here:
https://developers.paymaya.com/blog/entry/api-test-merchants-and-test-cards

### PayMaya Configuration

Set your configuration by initializing a PayMayaConfig enum.

``` kotlin
val payMayaConfig =
    if (BuildConfig.DEBUG) PayMayaConfig.SANDBOX
    else PayMayaConfig.PRODUCTION
```

### Enable Logging

By default, logging is enabled in *PayMayaConfig.SANDBOX* and disabled in *PayMayaConfig.PRODUCTION*. To manually enable logging:
```kotlin
Logger.DEBUGGABLE = true
```

### Get Payment Token Id

Here is an example of how to use this SDK with getting Payment Token Id.

1. Create an instance of PayMayaPaymentVault class.
``` kotlin
val payMayaPaymentVault = PayMayaPaymentVault(
       context, payMayaConfig,
       "{YOUR_CLIENT_KEY}"
       )
```

2. Create Card instance and supply the card details.

    * cardNumber: 12-16 Card Expiration Number, String
    * expMonth: 2-digit of Card Expiration Month, String
    * expYear: 4-digit of Card Expiration Year, String
    * cvv: 3-digit of Card CVV, String
    
``` kotlin
val card = Card(cardNumber, expMonth, expYear, cvv)
```

3. Create an instance of GetPaymentTokenCallback.
``` kotlin
val getPaymentTokenCallback = object: GetPaymentTokenCallback {
    override fun onStart() {}
    override fun onEnd() {}
    override fun onSuccess(paymentToken: PaymentToken?) {}
    override fun onError(requestProcessorError: RequestProcessorError?) {}
}
```

4. Use method getPaymentToken() to retrieve the PaymentToken object.
``` kotlin
payMayaPaymentVault.getPaymentToken(card, getPaymentTokenCallback)
```


### Testing

No tests yet.

## Contributing

Please see [CONTRIBUTING](CONTRIBUTING.md) for details.

### Security

If you discover any security related issues, please email kael.moreno@coreproc.ph instead of using the issue tracker.

## Credits

- [Kael Moreno](https://github.com/kaelitokael)
- [All Contributors](../../contributors)

## Support us

CoreProc is a software development company that provides software development services to startups, digital/ad agencies, and enterprises.

Learn more about us on our [website](https://coreproc.com).

## License

The MIT License (MIT). Please see [License File](LICENSE.md) for more information.
