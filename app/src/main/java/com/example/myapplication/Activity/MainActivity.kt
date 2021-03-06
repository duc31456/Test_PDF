package com.example.myapplication.Activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Activity.Adapter.ImportAdapter
import com.example.myapplication.Activity.Adapter.ToolAdapter
import com.example.myapplication.Model.ImportModel
import com.example.myapplication.Model.ToolModel
import com.example.myapplication.R
import com.itextpdf.io.image.ImageData
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Table
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*


class MainActivity : AppCompatActivity() {
    val x = 568
    val y = 840
    var m = 2
    var n = m + (1*m)
    val withtable = floatArrayOf(50f, 50f, 50f, 50F)

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    val IMAGES = arrayOf<Int>(
        R.drawable.anh1,
        R.drawable.anh2,
        R.drawable.anh3,
        R.drawable.anh4,
        R.drawable.anh5,
        R.drawable.anh6
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        mapping()
        load_recyclerImport()
        load_recyclerTool()
    }

    fun mapping()
    {
        val toolbar = findViewById<Toolbar>(R.id.toobar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val toggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout, toolbar, R.string.nav_stringopen, R.string.nav_stringclose
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigation_bottom.menu.findItem(R.id.navigation_tool).isChecked = true

    }


    //t???o file pdf
    fun createPDF(bitmap: ArrayList<Bitmap>) {
        //Create document file
        try {
            var pdfpath: String = Environment.getExternalStorageDirectory().absolutePath.toString()
            Log.d("string", pdfpath)
            val file: File = File(pdfpath, "mypdf.pdf")
            val output: OutputStream = FileOutputStream(file)
            val writer: PdfWriter = PdfWriter(file)
            val pdfdocument: PdfDocument = PdfDocument(writer)
            pdfdocument.defaultPageSize = PageSize.A4
            val document: Document = Document(pdfdocument)

            val table: Table = Table(withtable)

            var listImage: ArrayList<Image> = ArrayList()
            // th??m v??o m???ng image
            for (i in bitmap.indices) {
                val bos = ByteArrayOutputStream()
                bitmap.get(i).compress(Bitmap.CompressFormat.PNG, 0, bos)
                val bitmapdata = bos.toByteArray()
                var imagedata: ImageData = ImageDataFactory.create(bitmapdata)
                var image: Image = Image(imagedata)
                listImage.add(image)
            }
            //load ra pdf
            for(a in listImage.indices)
            {
            for (i in 0 until m) {
                for (j in 0 until n) {
                    run {
                       // document.add(listImage.get(a))
                            table.addCell(Cell().add(listImage.get(a).setAutoScale(true)))
                        }
                    }
                }
            }
            document.add(table)
            document.close()
            Toast.makeText(this,"T???o th??nh c??ng", Toast.LENGTH_SHORT).show()

            val target = Intent(Intent.ACTION_VIEW)
            target.setDataAndType(Uri.fromFile(file), "application/pdf")
            target.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            val intent = Intent.createChooser(target, "Open File")
            startActivity(intent);
            coordinateofcell()

        }catch (e: FileNotFoundException)
        {
            Log.d("AAA", "L???i" + e)
        }
    }

    fun coordinateofcell()
    {
        var heigh = n*(withtable.size-1)
        var with = withtable.size
        val h = Array(with) {
            IntArray(
                heigh
            )
        }
        val c = Array(with) { IntArray(heigh) }
        var demanh: Int = 0
      //  Log.d("AAA", ""+heigh + with)
            for (i in 0 until with) {
                for (j in 0 until heigh) {

                        if (j == 0 && i == 0) {
                            h[i][j] = 0
                            val toadoA: Int = h[i][j]
                            Log.d("Toa do $i,$j c??", "0, $toadoA")
                            demanh++
                        } else if (j == 0 && i != 0) {
                            h[i][j] = y / with * i
                            val toadoA: Int = h[i][j]
                            Log.d("Toa do $i,$j c??", "0, $toadoA")
                            demanh++
                        } else if (j != 0 && i == 0) {
                            c[i][j] = x / heigh * j
                            val toadoB: Int = c[i][j]
                            Log.d("Toa do $i,$j c??", "$toadoB ,0")
                            demanh++
                        } else if (j != 0 && i != 0) {
                            h[i][j] = x / with * i
                            c[i][j] = y / heigh * j
                            val toadoA: Int = h[i][j]
                            val toadoB: Int = c[i][j]
                            Log.d("Toa do $i,$j c??", "$toadoA,$toadoB")
                            demanh++
                        }
                }
            }
        Log.d("SHOW", "S??? l?????ng ???nh trong pdf l??: $demanh")

    }

    fun load_recyclerImport()
    {
        var item_import: ArrayList<ImportModel> = ArrayList()
        var adapter: ImportAdapter = ImportAdapter(this)
        add_importitem(item_import)
        adapter.setdata(item_import)
        recycler_import.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recycler_import.adapter = adapter
    }

    fun add_importitem(item_import: ArrayList<ImportModel>)
    {
        item_import.add(ImportModel(R.drawable.pc, "PC"))
        item_import.add(ImportModel(R.drawable.dropbox, "Drop Box"))
        item_import.add(ImportModel(R.drawable.ggdrive, "GG Drive"))
        item_import.add(ImportModel(R.drawable.files, "Files"))
    }

    fun load_recyclerTool()
    {
        var item_tool: ArrayList<ToolModel> = ArrayList()
        var adapter: ToolAdapter = ToolAdapter(this)
        add_toolitem(item_tool)
        adapter.setdata(item_tool)
        val gridLayoutManager = GridLayoutManager(this, 4)
        recycler_tool.layoutManager = gridLayoutManager
        recycler_tool.adapter = adapter
    }

    fun add_toolitem(item_tool: ArrayList<ToolModel>)
    {
        item_tool.add(ToolModel(R.drawable.anh1, "Convert To word"))
        item_tool.add(ToolModel(R.drawable.anh2, "Convert To excel"))
        item_tool.add(ToolModel(R.drawable.anh3, "Word to PDF"))
        item_tool.add(ToolModel(R.drawable.anh4, "PDF to epub"))
        item_tool.add(ToolModel(R.drawable.anh5, "Txt to PDF"))
        item_tool.add(ToolModel(R.drawable.anh6, "Ppoint to PDF"))
        item_tool.add(ToolModel(R.drawable.anh7, "Excel to PDF"))
        item_tool.add(ToolModel(R.drawable.anh8, "ODP to PDF"))
        item_tool.add(ToolModel(R.drawable.anh9, "ODT to PDF"))
        item_tool.add(ToolModel(R.drawable.anh10, "ODS to PDF"))

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.id_profile -> {
                var array: ArrayList<Bitmap> = ArrayList()
                for (i in IMAGES.indices)
                {
                    var bitmap: Bitmap = BitmapFactory.decodeResource(resources, IMAGES.get(i))
                    array.add(bitmap)
                }
                createPDF(array)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }
}