package uk.ac.stir.cs.UnitConversionApp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)

    /**
     * creates a list of ConversionInfo with hardcoded data to give something for user to work with
     * when the database is empty and launches application with a tablaout and creates the three
     * fragments that the will be changed via the tab layout
     * @param savedInstanceState is passed in
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val db = DatabaseHandler(this)
        if(db.readData("","id").isEmpty()){
            var defaultValues = getDefaultValues()
            for (i in defaultValues) {
                db.insertData(i)
            }
        }
        db.close()

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_page1))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_page2))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_page3))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val viewPager = findViewById<ViewPager>(R.id.pager)
        val adapter = PagerAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

    }

    /**
     * creates a list of ConversionInfo with hardcoded data to give something for user to work with
     * when the database is empty
     * @return Returns a list of ConversionInfo with default data
     */
    private fun getDefaultValues(): List<ConversionInfo> {
        return listOf<ConversionInfo>(
            ConversionInfo("Currency", "Pounds", 1.34, "Dollars"),
            ConversionInfo("Currency","Dollars",0.74,"Pounds"),
            ConversionInfo("Currency","Euro",0.90,"Pounds"),
            ConversionInfo("Currency","Pounds",1.11,"Euro"),
            ConversionInfo("Currency","Euro",1.21,"Dollars"),
            ConversionInfo("Currency","Dollars",0.82,"Euro"),
            ConversionInfo("Speed","mph",1.60934,"kph"),
            ConversionInfo("Speed","kph",0.621371,"mph"),
            ConversionInfo("Speed","knots",1.852,"kph"),
            ConversionInfo("Speed","kph",0.539957,"knots"),
            ConversionInfo("Speed","knots",1.15078,"mph"),
            ConversionInfo("Speed","mph",0.868976,"knots"),
            ConversionInfo("Volume","cups",236.588,"ml"),
            ConversionInfo("Volume","ml",0.00422675,"cups"),
            ConversionInfo("Volume","pints",2.4019,"cups"),
            ConversionInfo("Volume","cups",0.416337,"pints"),
            ConversionInfo("Volume","ml",0.00175975,"pints"),
            ConversionInfo("Volume","pints",568.261,"ml")
        )
    }
}