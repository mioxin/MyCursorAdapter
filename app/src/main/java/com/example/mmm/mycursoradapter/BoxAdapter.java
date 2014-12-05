package com.example.mmm.mycursoradapter;

/**
 * Created by palchuk on 04.12.2014.
 */

    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Date;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseAdapter;
    import android.widget.CheckBox;
    import android.widget.CompoundButton;
    import android.widget.CompoundButton.OnCheckedChangeListener;
    import android.widget.ImageView;
    import android.widget.TextView;
    public class BoxAdapter extends BaseAdapter {
        Context ctx;
        LayoutInflater lInflater;
        ArrayList<Article> objects;
        BoxAdapter(Context context, ArrayList<Article> articles) {
            ctx = context;
            objects = articles;
            lInflater = (LayoutInflater) ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        // кол-во элементов
        @Override
        public int getCount() {
            return objects.size();
        }
        // элемент по позиции
        @Override
        public Object getItem(int position) {
            return objects.get(position);
        }
        // id по позиции
        @Override
        public long getItemId(int position) {
            return position;
        }
        // пункт списка
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
// используем созданные, но не используемые view
            View view = convertView;
            if (view == null) {
                view = lInflater.inflate(R.layout.item, parent, false);
            }
            Article p = getArticle(position);
// заполняем View в пункте списка данными из товаров: наименование, цена
// и картинка
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            String ddd = sdf.format(p.adate);

            ((TextView) view.findViewById(R.id.tvTitle)).setText(p.title);
            ((TextView) view.findViewById(R.id.tvDescr)).setText(p.descr + "");
            ((ImageView) view.findViewById(R.id.ivImg)).setImageResource(p.img);
            ((TextView)view.findViewById(R.id.tvDate)).setText(ddd);
            CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
// присваиваем чекбоксу обработчик
            cbBuy.setOnCheckedChangeListener(myCheckChangList);
// пишем позицию
            cbBuy.setTag(position);
// заполняем данными из товаров: в корзине или нет
            cbBuy.setChecked(p.box);
            return view;
        }
        // товар по позиции
        Article getArticle(int position) {
            return ((Article) getItem(position));
        }
        // содержимое корзины
        ArrayList<Article> getBox() {
            ArrayList<Article> box = new ArrayList<Article>();
            for (Article p : objects) {
// если в корзине
                if (p.box)
                    box.add(p);
            }
            return box;
        }
        // обработчик для чекбоксов
        OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
// меняем данные товара (в корзине или нет)
                getArticle((Integer) buttonView.getTag()).box = isChecked;
            }
        };
    }
