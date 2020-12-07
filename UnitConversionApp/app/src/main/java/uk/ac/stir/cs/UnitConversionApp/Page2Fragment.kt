package uk.ac.stir.cs.UnitConversionApp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.page2_fragment.*

class Page2Fragment: Fragment() {
    private var multiplier: Double = 1.0
    private var requestedNumber: Double = 0.0

    /**
     * overrides onCreateView method with the page2_fragment.xml layout
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.page2_fragment, container, false)
    }

    @SuppressLint("FragmentLiveDataObserve")
    /**
     * overrides onViewCreated sets up the page2fragment and sets up button
     * listeners as well as updating the relvant info passed in from the
     * live data
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var currentConversionInfo : ConversionInfo = ConversionInfo()

        val model = ViewModelProviders.of(activity!!).get(ConversionViewModel::class.java)
        model.selectedConversion.observe(this, object: Observer<Any> {
            /**
             * overrides onChanged so the object passed in(the ConversionInfo selceted in
             * the page1fragment) is used to update the relevant text views and the multiplier
             * instance variable
             */
            override fun onChanged(o: Any?) {
                currentConversionInfo = o as ConversionInfo
                textView.text = currentConversionInfo.category
                multiplier = currentConversionInfo.multiplier
                textView2.setText(currentConversionInfo.convertFrom + ": ")
                textView3.setText(currentConversionInfo.convertTo + ": ")
                updateConversion()
            }
        })

        /**
         * sets up button 0 so it works like a calculators button
         * and calls updateConversion so the display is updated
         * with the new requested number
         */
        button0.setOnClickListener {
            requestedNumber = (requestedNumber * 10) + 0
            updateConversion()
        }

        /**
         * sets up button 1 so it works like a calculators button
         * and calls updateConversion so the display is updated
         * with the new requested number
         */
        button1.setOnClickListener {
            requestedNumber = (requestedNumber * 10) + 1
            updateConversion()
        }

        /**
         * sets up button 2 so it works like a calculators button
         * and calls updateConversion so the display is updated
         * with the new requested number
         */
        button2.setOnClickListener {
            requestedNumber = (requestedNumber * 10) + 2
            updateConversion()
        }

        /**
         * sets up button 3 so it works like a calculators button
         * and calls updateConversion so the display is updated
         * with the new requested number
         */
        button3.setOnClickListener {
            requestedNumber = (requestedNumber * 10) + 3
            updateConversion()
        }

        /**
         * sets up button 4 so it works like a calculators button
         * and calls updateConversion so the display is updated
         * with the new requested number
         */
        button4.setOnClickListener {
            requestedNumber = (requestedNumber * 10) + 4
            updateConversion()
        }

        /**
         * sets up button 5 so it works like a calculators button
         * and calls updateConversion so the display is updated
         * with the new requested number
         */
        button5.setOnClickListener {
            requestedNumber = (requestedNumber * 10) + 5
            updateConversion()
        }

        /**
         * sets up button 6 so it works like a calculators button
         * and calls updateConversion so the display is updated
         * with the new requested number
         */
        button6.setOnClickListener {
            requestedNumber = (requestedNumber * 10) + 6
            updateConversion()
        }

        /**
         * sets up button 7 so it works like a calculators button
         * and calls updateConversion so the display is updated
         * with the new requested number
         */
        button7.setOnClickListener {
            requestedNumber = (requestedNumber * 10) + 7
            updateConversion()
        }

        /**
         * sets up button 8 so it works like a calculators button
         * and calls updateConversion so the display is updated
         * with the new requested number
         */
        button8.setOnClickListener {
            requestedNumber = (requestedNumber * 10) + 8
            updateConversion()
        }

        /**
         * sets up button 9 so it works like a calculators button
         * and calls updateConversion so the display is updated
         * with the new requested number
         */
        button9.setOnClickListener {
            requestedNumber = (requestedNumber * 10) + 9
            updateConversion()
        }

        /**
         * sets up button so it sets the requested number to 0
         * and calls updateConversion so the display is updated
         * with the new requested number
         */
        buttonClear.setOnClickListener {
            requestedNumber = 0.0
            updateConversion()
        }

        /**
         * sets up button so it takes off the last digit in the number
         * and calls updateConversion so the display is updated
         * with the new requested number
         */
        buttonBackspace.setOnClickListener {
            requestedNumber = (requestedNumber - (requestedNumber % 10)) * 0.1
            updateConversion()
        }
    }

    /**
     * updates the display with the new requested number
     */
    private fun updateConversion() {
        textView4.setText(String.format("%.2f", requestedNumber))
        textView5.setText(String.format("%.2f", (requestedNumber * multiplier)))
    }
}